package top.lingkang.utils;

import java.util.List;

/**
 * @author lingkang
 * @date 2021/8/13 10:28
 * @description
 */
public class ArraysUtils {
    public static boolean isEmpty(Object[] objects) {
        return objects == null || objects.length == 0;
    }

    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }
}
