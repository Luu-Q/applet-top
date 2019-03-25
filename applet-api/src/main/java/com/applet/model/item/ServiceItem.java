package com.applet.model.item;

import com.applet.common.domain.BaseDomain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ServiceItem extends BaseDomain {

    private String name;

    private Integer parentId;

    private String itemNo;

    @JsonIgnore
    private Integer displayorder;

    private String itemPath;

    private String unit;

    private String desall;

    private String described;

    @JsonIgnore
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

    @JsonIgnore
    private Integer status;

    @JsonIgnore
    private Integer isDel;
}