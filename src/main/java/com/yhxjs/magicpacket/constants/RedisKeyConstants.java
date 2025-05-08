package com.yhxjs.magicpacket.constants;

public class RedisKeyConstants {

    public static final String USER_PREFIX = "user:";

    public static final String SESSION_PREFIX = "session:";

    public static final String CENTER_PREFIX = "center:";

    public static final String RATE_PREFIX = "rate:";

    public static final String USER_INFO = USER_PREFIX + "info:";

    public static final String USER_MODIFY_NICKNAME = USER_PREFIX + "modify:nickname:";

    public static final String USER_CHAT_LIMIT = USER_PREFIX + "chat:limit:";

    public static final String USER_SIGN = USER_PREFIX + "sign:";

    public static final String SESSION_IMG_CODE = SESSION_PREFIX + "imgCode:";

    public static final String SESSION_SLIDE_CODE = SESSION_PREFIX + "slideCode:";

    public static final String SESSION_VERIFY_SLIDE_CODE = SESSION_PREFIX + "verifySlideCode:";

    public static final String SESSION_SEND_KEY = SESSION_PREFIX + "key:";

    public static final String SESSION_SEND_KEY_CODE = SESSION_PREFIX + "keycode:";

    public static final String SESSION_SEND_KEY_CODE_TIME = SESSION_PREFIX + "keycodeTime:";

    public static final String SESSION_FORGET_EMAIL = SESSION_PREFIX + "forget:email:";
}
