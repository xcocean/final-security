package top.lingkang.error;

import javax.servlet.ServletException;

/**
 * @author lingkang
 * @date 2021/8/16 11:37
 * @description
 */
public class NotLoginException extends ServletException {
    public NotLoginException(String message) {
        super(message);
    }
}
