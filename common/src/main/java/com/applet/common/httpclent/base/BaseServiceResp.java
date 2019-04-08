package com.applet.common.httpclent.base;

/**
 * author:luning
 * Date:2018/11/12
 * Time:13:35
 * Description:  ${description}
 */
public class BaseServiceResp<T>  {

    public static final String RESPONSE_CODE_PENDING = "TP1009";

    private Integer code;//是否正常处理

    private String msg;	//消息

    private T data;			//传输数据

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

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
}
