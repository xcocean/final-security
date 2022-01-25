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

    String getType();

    void setType(String type);

    void updateLastAccessTime();

    long getLastAccessTime();

    long getCreateTime();
}
