package com.puke.lol.base;

import lombok.AllArgsConstructor;
import lombok.Data;

import static com.puke.lol.base.Constant.MSG_ERROR;
import static com.puke.lol.base.Constant.MSG_SUCCESS;

/**
 * @author zijiao
 * @version 18/4/20
 */
@Data
@AllArgsConstructor
public class Result<T> {

    private boolean success;
    private String msg;
    private T data;

    public static <T> Result<T> success(T data, String msg) {
        return new Result<>(true, msg, data);
    }

    public static <T> Result<T> success(T data) {
        return success(data, MSG_SUCCESS);
    }

    public static <T> Result<T> success() {
        return success(null, MSG_SUCCESS);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(false, msg, null);
    }

    public static <T> Result<T> error() {
        return error(MSG_ERROR);
    }

}
