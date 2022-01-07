package top.lingkang.error;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
public class FinalNotLoginException extends RuntimeException{
    public FinalNotLoginException(String message) {
        super(message);
    }
}
