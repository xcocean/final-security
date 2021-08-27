package top.lingkang.utils;

import top.lingkang.error.FinalException;

/**
 * @author lingkang
 * @date 2021/8/13 16:31
 * @description
 */
public class AssertUtils {
    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new FinalException(message);
        }
    }

    public static void isNotNull(Object object, String message) {
        if (object == null) {
            throw new FinalException(message);
        }
    }
}