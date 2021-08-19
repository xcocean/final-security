package top.lingkang.session;

/**
 * @author lingkang
 * @date 2021/8/13 14:58
 * @description
 */
public interface SessionListener {

    public void create(String id, String token);

    public void delete(String token);
}
