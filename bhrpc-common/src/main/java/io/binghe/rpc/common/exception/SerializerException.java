package io.binghe.rpc.common.exception;

/**
 * @author You Chuande
 */
public class SerializerException extends RuntimeException{
    private static final long serialVersionUID = -1L;

    public SerializerException(String s) {
        super(s);
    }

    public SerializerException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }

    public SerializerException(Throwable throwable) {
        super(throwable);
    }

    public SerializerException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
