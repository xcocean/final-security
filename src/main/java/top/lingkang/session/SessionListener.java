package top.lingkang.session;

import top.lingkang.entity.SessionEntity;

/**
 * @author lingkang
 * @date 2021/8/13 14:58
 * @description
 */
public interface SessionListener {

    public void create(String id, String token);

    public void delete(SessionEntity sessionEntity);
}
