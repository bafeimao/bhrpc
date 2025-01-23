package io.binghe.rpc.common.utils;

import java.util.stream.IntStream;

/**
 * @author You Chuande
 */
public class SerializationUtils {
    private static final String PADDING_STRING = "0";

    /**
     * 约定序列化类型最大长度为16
     */
    public static final int MAX_SERIALIZATION_TYPE_COUNT = 16;

    /**
     * 为长度不足16的字符串后面补0
     */
    public static String paddingString(String str) {
        str = transNullToEmpty(str);
        if (str.length() >= MAX_SERIALIZATION_TYPE_COUNT) {
            return str;
        }
        int paddingCount = MAX_SERIALIZATION_TYPE_COUNT - str.length();
        StringBuilder sb = new StringBuilder(str);
        IntStream.range(0, paddingCount).forEach(i -> sb.append(PADDING_STRING));
        return sb.toString();
    }

    public static String subString(String str) {
        str = transNullToEmpty(str);
        return str.replace(PADDING_STRING, "");
    }

    public static String transNullToEmpty(String str) {
        return str == null ? "" : str;
    }
}
