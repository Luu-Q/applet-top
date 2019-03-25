package com.applet.model.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ServiceItemType {

    @JsonIgnore
    private Integer id;

    private Integer itmeCode;

    private String name;

    @JsonIgnore
    private Integer displayorder;

    @JsonIgnore
    private Integer status;

}