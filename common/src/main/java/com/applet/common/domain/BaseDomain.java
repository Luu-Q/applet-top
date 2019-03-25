package com.applet.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseDomain implements Serializable {

    private Long id;
    private Long createTime;
    @JsonIgnore
    private Long updateTime;
}
