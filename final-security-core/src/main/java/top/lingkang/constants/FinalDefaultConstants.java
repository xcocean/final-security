package top.lingkang.constants;

/**
 * @author lingkang
 * @date 2021/8/29 13:15
 * @description
 */
public final class FinalDefaultConstants {

    public static final String logo = "\n" +
            "┌─┐┬┌┐┌┌─┐┬   ┌─┐┌─┐┌─┐┬ ┬┬─┐┬┌┬┐┬ ┬\n" +
            "├┤ ││││├─┤│───└─┐├┤ │  │ │├┬┘│ │ └┬┘\n" +
            "└  ┴┘└┘┴ ┴┴─┘ └─┘└─┘└─┘└─┘┴└─┴ ┴  ┴ ";

    public static final String tokenName = "final-token";

    // 默认session最大存活时间 默认 30分钟
    public static final long sessionMaxValid = 1800000L;

    // 每次访问是否更新会话时间，默认 true 即每次访问都会更新token的失效时间为最大
    public static final boolean accessUpdateSessionTime = true;
}
