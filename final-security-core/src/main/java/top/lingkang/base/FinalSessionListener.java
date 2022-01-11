package top.lingkang.base;

import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lingkang
 * @date 2022/1/8
 */
public interface FinalSessionListener {
    /**
     * 线程内登录，request为空值
     */
    void create(String token, String id, @Nullable HttpServletRequest request);
}
