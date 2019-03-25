package com.applet.entity.request.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ApiModel(value = "下单入参")
public class CreateOrderReq {

    @ApiModelProperty(value = "userId",example = "1")
    @NotNull(message = "userId不能为空")
    private Integer userId;

    @ApiModelProperty(value = "userId",example = "1")
    private String mobile;

    @ApiModelProperty(value = "服务项目ID",example = "1")
    @NotNull(message = "服务项目ID不能为空")
    private Integer siId;

    @ApiModelProperty(value = "服务地址ID",example = "1")
    @NotNull(message = "服务地址ID不能为空")
    private Integer addressId;

    @ApiModelProperty(value = "订单地址备注",example = "我是一个订单地址备注")
    private String addressRemark;

    @ApiModelProperty(value = "服务地址ID",example = "1")
    @Min(value = 1,message = "服务最小单位不能小于1")
    private Integer num;

    @ApiModelProperty(value = "订单需求",example = "我是一个订单需求")
    private String demand;
}
