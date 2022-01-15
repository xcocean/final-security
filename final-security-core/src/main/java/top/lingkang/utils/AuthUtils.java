package top.lingkang.utils;

import org.springframework.util.AntPathMatcher;
import top.lingkang.constants.FinalConstants;
import top.lingkang.error.FinalPermissionException;
import top.lingkang.filter.FinalFilterChain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lingkang
 * @date 2022/1/8
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
                if (matcher(h, r))
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

    public static void checkPermission(String[] permission, String[] has) {
        if (permission == null)
            return;
        if (has == null)
            throw new FinalPermissionException(FinalConstants.UNAUTHORIZED_MSG);
        for (String p : permission) {
            for (String h : has) {
                if (matcher(h, p))
                    return;
            }
        }
        throw new FinalPermissionException(FinalConstants.UNAUTHORIZED_MSG);
    }

    public static void checkAndPermission(String[] permission, String[] has) {
        if (permission == null)
            return;
        if (has == null)
            throw new FinalPermissionException(FinalConstants.UNAUTHORIZED_MSG);
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
     * 检查预留时间，返回true表示预留时间不满足
     */
    public static boolean checkReserveTime(long prepareTime, long maxTime, long lastAccessTime) {
        return lastAccessTime + maxTime - System.currentTimeMillis() < prepareTime;
    }

    /**
     * 超过最大有效时间返回true
     */
    public static boolean checkTokenValid(long lastTime, long maxTime) {
        return System.currentTimeMillis() - lastTime > maxTime;
    }

    public static FinalFilterChain[] addFilterChain(FinalFilterChain[] resource, FinalFilterChain... newChain) {
        if (resource == null || resource.length == 0) {
            resource = newChain;
        } else {
            int sourcesLen = resource.length;
            resource = Arrays.copyOf(resource, resource.length + 1);
            int init = 0;
            for (int i = sourcesLen - 1; i < resource.length; i++) {
                resource[i] = newChain[init];
                init++;
            }
        }
        return resource;
    }

    public static String[] removeRepeat(String[] str){
        Set<String> set=new HashSet<>();
        for (String s:str)
            set.add(s);
        return set.toArray(new String[set.size()]);
    }
}
