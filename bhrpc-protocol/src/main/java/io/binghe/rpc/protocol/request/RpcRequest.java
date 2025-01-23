package io.binghe.rpc.protocol.request;

import io.binghe.rpc.protocol.base.RpcMessage;

/**
 * @author You Chuande
 */
public class RpcRequest extends RpcMessage {

    private static final long serialVersionUID = 1L;

    /**
     * 类名称
     */
    private String className;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 参数类型数组
     */
    private Class<?>[] parameterTypes;

    /**
     * 参数数组
     */
    private Object[] parameters;

    /**
     * 版本号
     */
    private String version;

    /**
     * 服务分组
     */
    private String group;

    public String getClassName() {
        return className;
    }

    public RpcRequest setClassName(String className) {
        this.className = className;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public RpcRequest setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public RpcRequest setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
        return this;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public RpcRequest setParameters(Object[] parameters) {
        this.parameters = parameters;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public RpcRequest setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public RpcRequest setGroup(String group) {
        this.group = group;
        return this;
    }
}
