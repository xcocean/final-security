package top.lingkang.base;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lingkang
 * @date 2022/1/8
 */
public interface FinalSessionListener {
    void create(String token, String id, HttpServletRequest request);
}
