package top.lingkang.error;

public class FinalException extends IllegalArgumentException{

    public FinalException(String s) {
        super(s);
    }

    public FinalException(Throwable cause) {
        super(cause);
    }
}
