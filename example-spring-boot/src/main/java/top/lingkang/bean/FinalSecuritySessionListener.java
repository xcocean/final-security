package top.lingkang.bean;

import org.springframework.stereotype.Component;
import top.lingkang.session.FinalSession;
import top.lingkang.session.SessionListener;

/**
 * @author lingkang
 * @date 2021/8/13 15:08
 * @description
 */
@Component
public class FinalSecuritySessionListener implements SessionListener {
    public void create(String id, String token) {
        System.out.println("session-id创建" + token);
    }

    public void delete(FinalSession finalSession) {

        System.out.println("session-token删除" + finalSession.getToken());
    }
}
