package com.applet.model.item;

import com.applet.common.domain.BaseDomain;
import lombok.Data;

@Data
public class ServiceItemSku extends BaseDomain {

    private String name;

    private String itemNo;

    private Integer siId;

    private String itemPath;

    private String unit;

    private String desall;

    private String described;

    private Integer ifhour;

    private Integer minhour;

    private Integer lockhour;

    private String icon;

    private String headerUrl;

    private Integer upLimit;

    private Integer lowLimit;

    private Integer manNum;

    private Integer needArea;

    private Integer defaultVal;

    private Integer isWhenever;

    private Integer gender;

    private Integer displayorder;

    private Integer status;

    private Integer isDel;

}