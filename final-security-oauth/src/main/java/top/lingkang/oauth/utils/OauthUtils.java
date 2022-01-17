package top.lingkang.oauth.utils;

/**
 * @author lingkang
 * @date 2022/1/17
 */
public class OauthUtils {


    public static boolean exists(String[] source,String tag){
        for (String s:source){
            if (s.equals(tag)){
                return true;
            }
        }
        return false;
    }
}
