package com.applet.common.httpclent.base;

/**
 * author:luning
 * Date:2018/11/12
 * Time:13:35
 * Description:  ${description}
 */
public class BaseServiceExtResp<T>  {

    public static final String RESPONSE_CODE_PENDING = "TP1009";

    private Boolean success;//是否正常处理

    private String msg;	//消息

    private T data;			//传输数据

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

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
