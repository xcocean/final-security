package top.lingkang.utils;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * date 2021/8/13 15:40
 */
public class CookieUtils {
    public static void toZeroAge(String name, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);// 秒
        response.addCookie(cookie);
    }

    public static void addToken(HttpServletResponse response, String name, String value, int expiry) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(expiry);// 秒
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
