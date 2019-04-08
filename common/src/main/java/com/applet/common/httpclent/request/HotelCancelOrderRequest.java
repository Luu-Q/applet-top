package com.applet.common.httpclent.request;

import java.io.Serializable;

public class HotelCancelOrderRequest implements Serializable {
    private static final long serialVersionUID = 3517486949017656871L;
    private String order_id;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
