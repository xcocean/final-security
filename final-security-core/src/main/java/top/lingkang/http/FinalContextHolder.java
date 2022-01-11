package top.lingkang.http;

/**
 * @author lingkang
 * Created by 2022/1/11
 */
public abstract class FinalContextHolder {
    private static final ThreadLocal<FinalRequestContext> context = new ThreadLocal<>();

    public static FinalRequestContext getRequestContext() {
        return context.get();
    }

    public static void setRequestContext(FinalRequestContext requestContext) {
        context.set(requestContext);
    }

    public static void removeRequestContext() {
        context.remove();
    }
}
