package top.lingkang.base;

import org.springframework.lang.Nullable;
import top.lingkang.session.FinalSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * @date 2022/1/8
 */
public interface FinalSessionListener {
    /**
     * 线程内登录，request为空值
     */
    void create(FinalSession session, @Nullable HttpServletRequest request, @Nullable HttpServletResponse response);

    void delete(FinalSession session, @Nullable HttpServletRequest request, @Nullable HttpServletResponse response);
}
