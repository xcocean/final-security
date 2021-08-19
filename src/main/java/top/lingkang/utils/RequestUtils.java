package top.lingkang.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * @date 2021/8/13 10:25
 * @description
 */
public class RequestUtils {

    public static String getTokenByHeaders(String name, HttpServletRequest request) {
        return request.getHeader(name);
    }

    public static String getTokenByCookie(String name, Cookie[] cookies) {
        if (ArraysUtils.isEmpty(cookies)) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
