package top.lingkang.utils;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * @date 2021/8/13 15:40
 * @description
 */
public class CookieUtils {
    public static void addToken(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static String getTokenByCookie(String name, Cookie[] cookies) {
        if (cookies == null) {
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
