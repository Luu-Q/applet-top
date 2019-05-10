package com.applet.sms.common.exceptions;

/**
 * @Author LUNING
 * @Description 编码定义规则
 * 默认编码长度为6位
 * 开头两位是系统编码
 * 中间二位编码表示编码分类 例如
 * 00表示系统异常的编码
 * 01表示参数校验错误类的编码等等
 * 02表示业务状态不合法的编码
 * 03表示网络相关的异常的编码等
 * 后两位再根据前两位的分类定义具体的编码
 * 例如定义一个输入金额不能为空的异常码 0101
 * 定义一个 报文反序列化异常的编码0001
 * @create 2019-05-10 19:58
 * @return
 **/
public enum ApiResultEnum {
    //20000 状态的为返回成功值,尽量定1个值
    SUCCESS(0, "操作成功"),
    FAIL(1, "操作失败"),
    FAILED(400,"请求失败"),


    //30000~40000 状态为各种已经存在的状态
    INFO_IS_EXIST(30000, "信息已存在"),

    //40000~50000 状态的为各种查询失败的状态
    NO_DATA(40000, "数据不存在"),

    //50000~60000 状态的为各种更新操作失败状态
    ERROR(50000, "服务器错误"),
    ERROR_TRY_AGAIN(500100,"正在重试"),
    ERROR_TRY_AGAIN_FAILED(500101,"重试失败"),

    ;


    private int status;//返回状态值
    private String message;//返回信息

    private ApiResultEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public static String getResultMessage(int key) {
        ApiResultEnum[] values = ApiResultEnum.values();
        for (ApiResultEnum apiResultEnum : values) {
            if (apiResultEnum.getStatus() == key) {
                return apiResultEnum.getMessage();
            }
        }
        return "";
    }

}
