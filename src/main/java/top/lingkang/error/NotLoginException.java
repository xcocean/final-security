package top.lingkang.error;

/**
 * @author lingkang
 * @date 2021/8/16 11:37
 * @description
 */
public class NotLoginException extends RuntimeException {
    public NotLoginException(String message) {
        super(message);
    }
}
