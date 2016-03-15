package com.tsingda.smd.aspect.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NeedZkLock {
    public ZkLockType zkLockType() default ZkLockType.MUTEX;
    
    public String zkLockNode() default "";

    public enum ZkLockType {
        /**
         * 排它锁
         */
        MUTEX,

        /**
         * 共享锁（读）
         */
        SHARED_READ,
        
        /**
         * 共享锁（写）
         */
        SHARED_WRITE,
        
        /**
         * 数据库锁（读）
         */
        DB_ROW_READ,
        
        /**
         * 数据库锁（写）
         */
        DB_ROW_WRITE
    }
}
