package com.tsingda.smd.aspect;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.tsingda.smd.aspect.annotation.NeedZkLock;
import com.tsingda.smd.aspect.annotation.NeedZkLock.ZkLockType;
import com.tsingda.utils.ZkClientUtils;

@Component
@Aspect
public class ZkLockAspect {

    private static final Logger logger = LoggerFactory.getLogger(ZkLockAspect.class);

    private static final String LOCK_NODE_PATH_KEY = "lockNodePath";
    private static final String LOCK_NODE_PARENT_LISTENER_KEY = "lockNodeParentListener";

    @Autowired
    private CuratorFramework zkClient;

    @Pointcut(value = "execution(* com.tsingda.smd.service..AccountService.*(..)) && @annotation(com.tsingda.smd.aspect.annotation.NeedZkLock)")
    private void anyMethodWithZkLock() {
    }// 定义一个切入点

    @Before(value = "anyMethodWithZkLock() && args(accountId)")
    public void getLock(String accountId) {
        logger.info("before execution, {}", accountId);
    }

    @Around(value = "anyMethodWithZkLock() && @annotation(needZkLock)")
    public Object aroundNeedZkLock(ProceedingJoinPoint pjp, NeedZkLock needZkLock) throws Throwable {
        Object pjpResult = null;
        logger.info("开始获取Zk锁，类型：{}，LockNode：{}", needZkLock.zkLockType(), needZkLock.zkLockNode());
        // 1、获取锁
        String lockNode = needZkLock.zkLockNode();
        PathChildrenCache cache = null;
        Map<String, Object> mapResult = null;
        if (!StringUtils.isEmpty(lockNode)) {
            logger.warn("@NeedZkLock需要指定非空lockNode，否则会失效");
            CountDownLatch latch = new CountDownLatch(1);
            mapResult = getLock(lockNode, latch, needZkLock.zkLockType());
            cache = (PathChildrenCache) mapResult.get(LOCK_NODE_PARENT_LISTENER_KEY);
            lockNode = (String) mapResult.get(LOCK_NODE_PATH_KEY);
            // 等待获取到锁
            latch.await();
        }

        // 2、执行业务方法
        logger.info("获取到锁：{}，开始执行业务调用", lockNode);
        pjpResult = pjp.proceed();
        // 3、清理（close listener and delete node）
        logger.info("业务方法执行完成，清理垃圾，LockNode={}", lockNode);
        if (cache != null) {
            cache.close();
        }
        if (!StringUtils.isEmpty(lockNode) && ZkClientUtils.checkExists(zkClient, lockNode) != null) {
            ZkClientUtils.deleteNode(zkClient, lockNode);
        }

        // 4、返回业务方法调用结果
        return pjpResult;

    }

    private Map<String, Object> getLock(String lockNode, CountDownLatch latch, ZkLockType zkLockType) throws Exception {
        Map<String, Object> mapResult = new HashMap<String, Object>();
        String lockParentNode = lockNode.substring(0, lockNode.lastIndexOf('/'));
        String myLockNode = ZkClientUtils.createNode(zkClient, lockNode, CreateMode.EPHEMERAL_SEQUENTIAL, zkLockType
                .toString().getBytes(), null);
        PathChildrenCacheListener listener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                List<String> children = ZkClientUtils.getChildren(zkClient, lockParentNode);
                Collections.sort(children);
                String firstChild = children.get(0);
                logger.info("我的锁：{}", myLockNode);
                logger.info("当前锁：{}", firstChild);
                if (myLockNode.endsWith("/" + firstChild)) {
                    logger.info("获取到锁：{}", firstChild);
                    latch.countDown();
                } else {
                    logger.info("未获取到锁，继续等待");
                }
            }
        };
        PathChildrenCache cache = ZkClientUtils.addChildrenWatcher(zkClient, lockParentNode, listener);
        mapResult.put(LOCK_NODE_PATH_KEY, myLockNode);
        mapResult.put(LOCK_NODE_PARENT_LISTENER_KEY, cache);
        return mapResult;
    }

}
