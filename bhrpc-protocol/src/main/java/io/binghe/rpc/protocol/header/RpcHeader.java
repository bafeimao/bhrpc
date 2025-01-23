package io.binghe.rpc.protocol.header;

import java.io.Serializable;

/**
 * @author You Chuande
 */
public class RpcHeader implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    +---------------------------------------------------------------+
    |魔数 2byte | 报文类型 1byte | 状态 1byte | 消息 ID 8byte |
    +---------------------------------------------------------------+
    | 序列化类型 16byte   |  数据长度 4byte   |
    +---------------------------------------------------------------+
     */
    /**
     * 魔数 2字节
     */
    private short magic;
    /**
     * 报文类型 1字节
     */
    private byte msgType;

    /**
     * 状态 1字节
     */
    private byte status;

    /**
     * 消息 ID 8字节
     */
    private long requestId;

    /**
     * 序列化类型 16字节,不足16字节后面补0，约定序列化类型长度最多不能超过15
     */
    private String serializationType;
    /**
     * 消息长度 4字节
     */
    private int msgLen;

    public short getMagic() {
        return magic;
    }

    public RpcHeader setMagic(short magic) {
        this.magic = magic;
        return this;
    }

    public byte getMsgType() {
        return msgType;
    }

    public RpcHeader setMsgType(byte msgType) {
        this.msgType = msgType;
        return this;
    }

    public byte getStatus() {
        return status;
    }

    public RpcHeader setStatus(byte status) {
        this.status = status;
        return this;
    }

    public long getRequestId() {
        return requestId;
    }

    public RpcHeader setRequestId(long requestId) {
        this.requestId = requestId;
        return this;
    }

    public String getSerializationType() {
        return serializationType;
    }

    public RpcHeader setSerializationType(String serializationType) {
        this.serializationType = serializationType;
        return this;
    }

    public int getMsgLen() {
        return msgLen;
    }

    public RpcHeader setMsgLen(int msgLen) {
        this.msgLen = msgLen;
        return this;
    }
}
