package io.binghe.rpc.constants;

/**
 * @author You Chuande
 */
public class RpcConstants {
    /**
     * message header 32 byte
     */
    public static final int HEADER_TOTAL_LEN = 32;

    /**
     * magic number
     */
    public static final short MAGIC = 0x10;

    /**
     * version
     */
    public  static final byte VERSION = 0x1;

    /**
     * REFLECT_TYPE_JDK
     */
    public static final String REFLECT_TYPE_JDK = "jdk";

    /**
     * REFLECT_TYPE_CGLIB
     */
    public static final String REFLECT_TYPE_CGLIB = "cglib";
    /**
     * DEFLECT_TYPE_BYTE_BUDDY
     */
    public static final String REFLECT_TYPE_BYTE_BUDDY = "byteBuddy";

    /**
     * javassist dynamic proxy
     */
    public static final String PROXY_JAVASSIST = "javassist";

    /**
     * cglib dynamic proxy
     */
    public static final String PROXY_CGLIB = "cglib";

    /**
     * init method
     */
    public static final String INIT_METHOD_NAME = "init";

    /**
     * zookeeper
     */
    public static final String REGISTRY_CENTER_ZOOKEEPER = "zookeeper";

    /**
     * nacos
     */
    public static final String REGISTRY_CENTER_NACOS = "nacos";

    /**
     * apoll
     */
    public static final String REGISTRY_CENTER_APOLL = "apoll";

    /**
     * etcd
     */
    public static final String REGISTRY_CENTER_ETCD = "etcd";

    /**
     * eureka
     */
    public static final String REGISTRY_CENTER_EUREKA = "eureka";

    /**
     * protostuff serialization
     */
    public static final String SERIALIZATION_PROTOSTUFF = "protostuff";

    /**
     * FST serialization
     */
    public static final String SERIALIZATION_FST = "fst";

    /**
     * hessian 2 serialization
     */
    public static final String SERIALIZATION_HESSIAN2 = "hessian2";

    /**
     * jdk serialization
     */
    public static final String SERIALIZATION_JDK = "jdk";

    /**
     * json serialization
     */
    public static final String SERIALIZATION_JSON = "json";

    /**
     * kryo serialization
     */
    public static final String SERIALIZATION_KRYO = "kryo";

    /**
     * 随机负载均衡
     */
    public static final String SERVER_LOAD_BALANCER_RANDOM = "random";

    /**
     * server load balance base on ZK
     */
    public static final String SERVER_LOAD_BALANCER_ZKCONSISTENTHASH = "zkconsistenthash";

    /**
     * 增强型负载均衡前缀
     */
    public static final String SERVICE_ENHANCED_LOAD_BALANCER_PREFIX = "enhanced_";

    /**
     * 最小权重
     */
    public static final int SERVICE_WEIGHT_MIN = 1;
    /**
     * 最大权重
     */
    public static final int SERVICE_WEIGHT_MAX = 100;

}
