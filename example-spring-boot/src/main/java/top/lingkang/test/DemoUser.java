package top.lingkang.test;

import java.io.Serializable;

/**
 * @author lingkang
 * date 2022/1/23
 */
public class DemoUser implements Serializable {

    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
