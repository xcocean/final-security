package top.lingkang.utils;

import org.springframework.util.AntPathMatcher;
import top.lingkang.constants.FinalConstants;
import top.lingkang.error.FinalPermissionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author lingkang
 * @date 2022/1/8
 */
public class AuthUtils {
    private static final AntPathMatcher matcher = new AntPathMatcher();

    public static boolean matcher(String pattern, String path) {
        return matcher.match(pattern, path);
    }

    public static void checkRole(String[] roles, List<String> has) {
        if (roles == null)
            return;
        for (String r : roles) {
            for (String h : has) {
                if (matcher(h, r))
                    return;
            }
        }
        throw new FinalPermissionException(FinalConstants.UNAUTHORIZED_MSG);
    }

    public static void checkAndRole(String[] roles, List<String> has) {
        if (roles == null)
            return;
        for (String r : roles) {
            boolean no = true;
            for (String h : has) {
                if (matcher(h, r)) {
                    no = false;
                    continue;
                }
            }
            if (no) {
                throw new FinalPermissionException(FinalConstants.UNAUTHORIZED_MSG);
            }
        }
    }

    public static void checkPermission(String[] permission, List<String> has) {
        if (permission == null)
            return;
        for (String p : permission) {
            for (String h : has) {
                if (matcher(h, p))
                    return;
            }
        }
        throw new FinalPermissionException(FinalConstants.UNAUTHORIZED_MSG);
    }

    public static void checkAndPermission(String[] permission, List<String> has) {
        if (permission == null)
            return;
        for (String p : permission) {
            boolean no = true;
            for (String h : has) {
                if (matcher(h, p)) {
                    no = false;
                    continue;
                }
            }
            if (no) {
                throw new FinalPermissionException(FinalConstants.UNAUTHORIZED_MSG);
            }
        }
    }

    public static void requestDispatcher(String url, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查预留时间，返回true表示时间不满足后续操作
     */
    public static boolean checkReserveTime(long length, long maxValid, long lastAccessTime) {
        return lastAccessTime + maxValid - System.currentTimeMillis() < length;
    }
}
