package com.applet.common.wxace;


/**
 * error code 说明.
 * <p>
 * 41001: encodingAesKey 非法
 * 41003: aes 解密失败
 * 41004: 解密后得到的buffer非法
 * 41005: base64加密失败
 * 41016: base64解密失败
 */
public class AesException extends Exception {

    public final static int IllegalAesKey      = -41001;
    public final static int IllegalIv          = -41002;
    public final static int IllegalBuffer      = -41003;
    public final static int DecodeBase64Error  = -41004;
    public final static int EncryptBase64Error = -41005;
    public final static int IllegalSessionKey = -41006;
    public final static int IllegalIV = -41007;
    public final static int ComputeSignatureError = -41008;

    private int code;

    private static String getMessage(int code) {
        switch (code) {
            case IllegalAesKey:
                return "encodingAesKey 非法";
            case IllegalIv:
                return "aes 解密失败";
            case IllegalBuffer:
                return "解密后得到的buffer非法";
            case DecodeBase64Error:
                return "base64解密失败";
            case EncryptBase64Error:
                return "base64加密失败";
            case IllegalSessionKey:
                return "SessionKey非法";
            case IllegalIV:
                return "IV非法";
            case ComputeSignatureError:
                return "sha加密生成签名失败";
            default:
                return null;
        }
    }

    public int getCode() {
        return code;
    }

    AesException(int code) {
        super(getMessage(code));
        this.code = code;
    }
}
