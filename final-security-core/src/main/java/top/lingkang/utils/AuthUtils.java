package top.lingkang.utils;

import top.lingkang.constants.FinalConstants;
import top.lingkang.error.FinalPermissionException;

/**
 * @author lingkang
 * date 2022/1/8
 */
public class AuthUtils {
    private static final AntPathMatcher matcher = new AntPathMatcher();

    public static boolean matcher(String pattern, String path) {
        return matcher.match(pattern, path);
    }

    /**
     * 入参均不能为空！长度大于 1
     * @param roles not empty
     * @param has not empty
     */
    public static void checkRole(String[] roles, String[] has) {
        for (String r : roles) {
            for (String h : has) {
                if (r.equals(h))
                    return;
            }
        }
        throw new FinalPermissionException(FinalConstants.UNAUTHORIZED_MSG);
    }

    /**
     * 入参均不能为空！长度大于 1
     * @param roles not empty
     * @param has not empty
     */
    public static void checkAndRole(String[] roles, String[] has) {
        for (String r : roles) {
            boolean no = true;
            for (String h : has) {
                if (r.equals(h)) {
                    no = false;
                    break;
                }
            }
            if (no) {
                throw new FinalPermissionException(FinalConstants.UNAUTHORIZED_MSG);
            }
        }
    }

}
