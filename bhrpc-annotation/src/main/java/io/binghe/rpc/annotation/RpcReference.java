package io.binghe.rpc.annotation;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author You Chuande
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Autowired
public @interface RpcReference {
    /**
     * 版本号
     */
    String version() default "1.0.0";

    /**
     * 注册中心类型 目前包含 zookeeper，nacos，etcd,consul
     */
    String registryType() default "zookeeper";

    /**
     * 注册地址
     */
    String registryAddress() default "127.0.0.1:2181";

    /**
     * 负载均衡类型，默认基于ZK的一致性Hash
     */
    String loadBalanceType() default "zkconsistenthash";

    /**
     * 序列化类型 目前的类型包括: protostuff,kryo,json,jdk,hessian2,fst
     */
    String serializationType() default "protostuff";

    /**
     * 超时时间 默认5s
     */
    long timeout() default 5000;

    /**
     * 是否异步执行
     */
    boolean async() default false;

    /**
     * 是否单向调用
     */
    boolean oneway() default false;

    /**
     * 代理的类型, jdk; javassist ,cglib
     */
    String proxy() default "jdk";

    /**
     * 服务分组,默认为空
     */
    String group() default "";

}
