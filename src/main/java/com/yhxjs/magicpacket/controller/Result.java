package com.yhxjs.magicpacket.controller;

import com.yhxjs.magicpacket.enums.Operate;
import lombok.Data;

@Data
public class Result {

    private Integer code;

    private Object data;

    private String msg;

    public Result(Integer code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public Result(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

    public static Result querySuccess(Object data) {
        return new Result(Code.QUERY_SUCCESS, data);
    }

    public static Result queryZero() {
        return new Result(Code.QUERY_ZERO, null, "啥也木有");
    }

    public static Result success(Integer successCode, Object data, String successMsg) {
        return new Result(successCode, data, successMsg);
    }

    public static Result success(Integer successCode, String successMsg) {
        return success(successCode, true, successMsg);
    }

    public static Result error(Integer errorCode, String errorMsg) {
        return new Result(errorCode, false, errorMsg);
    }

    public static Result success(Operate operate, Object data, String successMsg) {
        switch (operate) {
            case INSERT:
                return success(Code.INSERT_SUCCESS, data, successMsg);
            case DELETE:
                return success(Code.DELETE_SUCCESS, data, successMsg);
            case UPDATE:
                return success(Code.UPDATE_SUCCESS, data, successMsg);
            case QUERY:
                return success(Code.QUERY_SUCCESS, data, successMsg);
            default:
                return success(Code.OK, data, successMsg);
        }
    }

    public static Result success(Operate operate, String successMsg) {
        switch (operate) {
            case INSERT:
                return success(Code.INSERT_SUCCESS, true, successMsg);
            case DELETE:
                return success(Code.DELETE_SUCCESS, true, successMsg);
            case UPDATE:
                return success(Code.UPDATE_SUCCESS, true, successMsg);
            case QUERY:
                return success(Code.QUERY_SUCCESS, true, successMsg);
            default:
                return success(Code.OK, true, successMsg);
        }
    }

    public static Result error(Operate operate, String errorMsg) {
        switch (operate) {
            case INSERT:
                return error(Code.INSERT_FAIL, errorMsg);
            case DELETE:
                return error(Code.DELETE_FAIL, errorMsg);
            case UPDATE:
                return error(Code.UPDATE_FAIL, errorMsg);
            case QUERY:
                return error(Code.QUERY_FAIL, errorMsg);
            default:
                return error(Code.FAIL, errorMsg);
        }
    }
}
