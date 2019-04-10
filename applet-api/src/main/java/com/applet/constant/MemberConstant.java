package com.applet.constant;


public class MemberConstant {
    // 加密
    public static final int SALT_SIZE = 8;
    public static final int HASH_INTERATIONS = 1024;

    // 登录toekn前缀
    public static final String LOGIN_TOKEN_KEY_PREFIX =  "app-user-token-";  // 秒

    // 登录Token失效时间
    public static final int TOKEN_EXPIRES_TIME = 604800;  // 秒

    // 登录验证码失效时间
    public static final int LOGIN_VERIFICATION_CODE__EXPIRES_TIME = 60;  // 秒


}
