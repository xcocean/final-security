package top.lingkang.oauth.dto;

import java.io.Serializable;

/**
 * @author lingkang
 * Created by 2022/1/21
 */
public class ResponseResult<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public ResponseResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
