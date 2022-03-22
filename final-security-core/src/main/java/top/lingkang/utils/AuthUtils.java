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

    public static void checkRole(String[] roles, String[] has) {
        if (roles == null)
            return;
        if (has == null)
            throw new FinalPermissionException(FinalConstants.UNAUTHORIZED_MSG);
        for (String r : roles) {
            for (String h : has) {
                if (r.equals(h))
                    return;
            }
        }
        throw new FinalPermissionException(FinalConstants.UNAUTHORIZED_MSG);
    }

    public static void checkAndRole(String[] roles, String[] has) {
        if (roles == null)
            return;
        if (has == null)
            throw new FinalPermissionException(FinalConstants.UNAUTHORIZED_MSG);
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
