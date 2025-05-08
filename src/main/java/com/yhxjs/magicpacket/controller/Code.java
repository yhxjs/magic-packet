package com.yhxjs.magicpacket.controller;

public class Code {

    public static final Integer OK = 1;
    public static final Integer FAIL = 0;
    //登录状态码
    public static final Integer LOGIN_SUCCESS = 201;
    public static final Integer LOGIN_FAIL = 200;
    //验证码状态码
    public static final Integer CODE_PASS = 221;
    public static final Integer CODE_FAIL = 220;
    //查询状态码
    public static final Integer QUERY_SUCCESS = 231;
    public static final Integer QUERY_FAIL = 230;
    public static final Integer QUERY_ZERO = 232;
    //修改状态码
    public static final Integer UPDATE_SUCCESS = 241;
    public static final Integer UPDATE_FAIL = 240;
    //删除状态码
    public static final Integer DELETE_SUCCESS = 251;
    public static final Integer DELETE_FAIL = 250;
    //新增状态码
    public static final Integer INSERT_SUCCESS = 261;
    public static final Integer INSERT_FAIL = 260;
    //非法状态码
    public static final Integer ACCESS_ILLEGAL = 500;
    public static final Integer TOKEN_FAIL = 501;
    public static final Integer SYSTEM_ERROR = 502;
}
