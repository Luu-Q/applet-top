package com.applet.entity.result;

import com.applet.entity.enums.RetCodeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ApiModel(value = "统一响应对象")
@Data
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultModel<T> {

    @ApiModelProperty(value = "处理成功与否，0 成功，非0失败", required = true)
    private Integer code;
    @ApiModelProperty(value = "成功或失败提示", required = true)
    private String msg;
    @ApiModelProperty(value = "返回值对象")
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
