package com.applet.common.result;

import com.applet.common.enums.RetCodeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultModel<T> {

    private Integer code;
    private String msg;
    private T data;

    public static ResultModel succ() {
        return ResultModel.builder().code(RetCodeEnum.SUCC.toValue()).msg(RetCodeEnum.SUCC.toMsg()).build();
    }

    public static ResultModel succWithData(Object data) {
        return ResultModel.builder().code(RetCodeEnum.SUCC.toValue()).msg(RetCodeEnum.SUCC.toMsg()).data(data).build();
    }

    public static ResultModel fail(String msg) {
        return ResultModel.builder().code(RetCodeEnum.FAIL.toValue()).msg(msg).build();
    }

    public static ResultModel fail(String code,String msg) {
        return ResultModel.builder().code(Integer.valueOf(code)).msg(msg).build();
    }

    public static ResultModel fail(RetCodeEnum retCodeEnum) {
        return ResultModel.builder().code(retCodeEnum.toValue()).msg(retCodeEnum.toMsg()).build();
    }

    public static ResultModel unknowWithMsg(String msg) {
        return ResultModel.builder().code(RetCodeEnum.UNKNOWN.toValue()).msg(msg).build();
    }

    public boolean succeed() {
        return this.code == RetCodeEnum.SUCC.toValue();
    }

}
