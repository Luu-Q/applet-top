package com.applet.sms.common.retryaop;


import com.applet.sms.common.exceptions.ApiException;
import com.applet.sms.common.exceptions.ApiResultEnum;

/**
 * 更新重试异常
 */
public class LockFailRetryException extends ApiException {

    public LockFailRetryException(ApiResultEnum apiResultEnum) {
        super(apiResultEnum);
    }

}
