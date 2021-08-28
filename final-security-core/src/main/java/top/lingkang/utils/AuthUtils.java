package top.lingkang.utils;

import java.util.regex.Pattern;

/**
 * @author lingkang
 * @date 2021/8/21 17:34
 * @description
 */
public class AuthUtils {

    /**
     * 模式匹配
     * 校验权限是否存在，例如：
     * use*           user            true
     * user-          user            false
     * user-*-update  user-ok-update  true
     * user-*-update  user-ok         false
     */
    public static boolean patternMatch(String pattern, String str) {
        if (pattern.indexOf("*") == -1) {
            return pattern.equals(str);
        }
        return Pattern.matches(pattern.replaceAll("\\*", ".*"), str);
    }
}
