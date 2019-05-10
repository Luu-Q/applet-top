package com.applet.sms.common.exceptions;


public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 6716067579749436205L;

    private int code;//返回状态值
    private String desc;

    public ApiException(ApiResultEnum responseCode) {
        this.code = responseCode.getStatus();
        this.desc = responseCode.getMessage();
    }
    
    public ApiException(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public ApiException(ApiResultEnum responseCode, String desc) {
        this.code = responseCode.getStatus();
        this.desc = desc;
    }
    public ApiException(ApiResultEnum errorEnum, Throwable t) {
        super(t);
        this.code = errorEnum.getStatus();
        this.desc = errorEnum.getMessage();
    }

    public ApiException(ApiResultEnum errorEnum, String desc, Throwable t) {
        super(t);
        this.code = errorEnum.getStatus();
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
		return desc;
	}

    /*
     * (non-Javadoc)
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage() {
        return "errorCode:" + this.code + ";desc:" + this.desc;
    }

	

}
