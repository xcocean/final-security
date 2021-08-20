package top.lingkang.session;

import top.lingkang.entity.SessionEntity;

/**
 * @author lingkang
 * @date 2021/8/20 10:11
 * @description
 */
public interface SessionManager {
    SessionEntity get(String token);

    void put(String token, SessionEntity sessionEntity);

    void remove(String token);

    boolean containsKey(String token);
}