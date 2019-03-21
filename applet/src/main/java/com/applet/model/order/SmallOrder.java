package com.applet.model.order;

import com.applet.common.domain.BaseDomain;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SmallOrder extends BaseDomain {

    private Integer userId;

    private String mobile;

    private String orderNo;

    private Integer siId;

    private String serviceName;

    private Integer addressId;

    private String addressRemark;

    private BigDecimal unitPrice;

    private Integer num;

    private BigDecimal sumPrice;

    private BigDecimal additionalMoney;

    private String demand;

    private Integer orderStatus;

    private Integer tradeId;


}