package top.lingkang.http;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lingkang
 * Created by 2022/2/11
 * HttpServletRequest上下文的使用
 */
public abstract class FinalRequestContext {
    private static final ThreadLocal<HttpServletRequest> context = new ThreadLocal<>();

    public static HttpServletRequest getRequest() {
        return context.get();
    }

    public static void setRequest(HttpServletRequest request) {
        context.set(request);
    }

    public static void removeRequest() {
        context.remove();
    }
}
