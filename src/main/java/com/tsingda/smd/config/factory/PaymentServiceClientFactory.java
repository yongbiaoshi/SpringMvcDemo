package com.tsingda.smd.config.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent.Type;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.tsingda.service.payment.api.PaymentService;
import com.tsingda.service.payment.api.PaymentService.Client;
import com.tsingda.utils.TClientUtils;
import com.tsingda.utils.ZkClientUtils;

public class PaymentServiceClientFactory {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceClientFactory.class);

    @SuppressWarnings("unused")
    private CuratorFramework zkClient;
    @SuppressWarnings("unused")
    private String serviceNodePath;

    private int tTimeout;

    private Random random = new Random();

    private Map<String, PaymentService.Client> clientMapCache = new HashMap<String, PaymentService.Client>();

    @SuppressWarnings("unused")
    private PaymentServiceClientFactory() {
    }

    public PaymentServiceClientFactory(String path, CuratorFramework zkClient, int timeout) throws Exception {

        Assert.notNull(zkClient, "Zookeeper client can not be null");
        Assert.hasText(path, "TService node path in zk must not be empty");
        this.zkClient = zkClient;
        this.serviceNodePath = path;
        this.tTimeout = timeout;

        PathChildrenCacheListener listener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                Type type = event.getType();
                logger.info("child change type:{}", type);
                String childPath = null;
                if (event.getData() != null) {
                    childPath = event.getData().getPath();
                }

                switch (type) {
                case CHILD_ADDED:
                    clientMapCache.put(childPath, newTClient(event.getData()));
                    break;
                case CHILD_REMOVED:
                    clientMapCache.remove(childPath);
                    break;
                case CONNECTION_LOST:
                    clientMapCache.clear();
                    break;
                case CONNECTION_RECONNECTED:
                    initAllClient(path, zkClient);
                    break;
                default:
                    break;
                }
            }
        };
        ZkClientUtils.addChildrenWatcher(zkClient, path, listener);
    }

    // 随机获取一个TServiceClient

    /**
     * 随机获取一个TServiceClient(或者使用其它策略）
     *
     * @param path zk node path
     * @return thrift service client
     */
    public PaymentService.Client getClient() {
        Assert.notEmpty(clientMapCache, "没有可用的PaymentService");
        Object[] keys = clientMapCache.keySet().toArray();
        int index = random.nextInt(keys.length);
        System.out.println(keys[index]);
        logger.info("获取PaymentService client：{},共：{}", keys[index], keys.length);
        return clientMapCache.get(keys[index]);
    }

    private void initAllClient(String path, CuratorFramework zkClient) throws Exception {
        List<String> children = ZkClientUtils.getChildren(zkClient, path);
        for (String childPath : children) {
            byte[] data = ZkClientUtils.readNode(zkClient, childPath, null);
            Client client = newTClient(data);
            clientMapCache.put(childPath, client);
        }
    }

    private PaymentService.Client newTClient(ChildData data) {
        return newTClient(data.getData());
    }

    private PaymentService.Client newTClient(byte[] data) {
        String service = new String(data);
        if (service == null || !service.contains(":")) {
            return null;
        }
        return TClientUtils.newTClient(service.split(":")[0], Integer.valueOf(service.split(":")[1]), this.tTimeout,
                PaymentService.Client.class);
    }
}
