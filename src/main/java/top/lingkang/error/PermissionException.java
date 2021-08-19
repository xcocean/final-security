package top.lingkang.error;

/**
 * @author lingkang
 * @date 2021/8/19 16:41
 * @description
 */
public class PermissionException extends RuntimeException {
    public PermissionException(String message) {
        super(message);
    }
}
