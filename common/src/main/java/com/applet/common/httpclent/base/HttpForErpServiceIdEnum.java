package com.applet.common.httpclent.base;

/**
 * Created by luning
 */
public enum HttpForErpServiceIdEnum {
    TongxingOrderService("/bom/api/tuan/sale_todo_add_order", "同行下单"),
    ;
    
    private String serviceId;
    private String desc;
    
    HttpForErpServiceIdEnum(String serviceId, String desc) {
        this.serviceId = serviceId;
        this.desc = desc;
    }
    
    public String getServiceId() {
        return serviceId;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
