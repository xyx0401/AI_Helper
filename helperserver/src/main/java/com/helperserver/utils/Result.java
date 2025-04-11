package com.helperserver.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public boolean isSuccess() {
        return code != null && code == 200;
    }

    public String getMessage() {
        return msg;
    }

    public static Result success() {
        Result result = new Result();
        result.setCode(200);
        result.setMsg("AI回答已生成");
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        if (data instanceof String) {
            result.setMsg((String) data);
        } else if (data != null && data.toString().length() > 0) {
            result.setMsg(data.toString());
        } else {
            result.setMsg("操作成功");
        }
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg(message);
        result.setData(data);
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.setCode(500);
        result.setMsg(msg);
        return result;
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}