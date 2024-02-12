package com.st.common.model;

import com.st.common.constant.Constants;

import java.io.Serializable;

/**
 * @author leon
 * @date 2024/2/12 11:23
 */
public class ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 成功
     */
    public static final int SUCCESS = Constants.SUCCESS;

    /**
     * 失败
     */
    public static final int FAIL = Constants.FAIL;

    private int code;

    private String msg;

    private T data;

    public static <T> ResponseResult<T> ok() {
        return restResult(null, SUCCESS, null);
    }

    public static <T> ResponseResult<T> ok(T data) {
        return restResult(data, SUCCESS, null);
    }

    public static <T> ResponseResult<T> ok(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> ResponseResult<T> fail() {
        return restResult(null, FAIL, null);
    }

    public static <T> ResponseResult<T> fail(String msg) {
        return restResult(null, FAIL, msg);
    }

    public static <T> ResponseResult<T> fail(T data) {
        return restResult(data, FAIL, null);
    }

    public static <T> ResponseResult<T> fail(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

    public static <T> ResponseResult<T> fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    private static <T> ResponseResult<T> restResult(T data, int code, String msg) {
        ResponseResult<T> apiResult = new ResponseResult<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
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
