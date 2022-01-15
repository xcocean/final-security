package top.lingkang.session;


/**
 * @author lingkang
 * Created by 2022/1/7
 */
public interface FinalSession {
    String getId();

    String getToken();

    Object getUser();

    void setUser(Object user);

    void updateLastAccessTime();

    long getLastAccessTime();

    long getCreateTime();
}
