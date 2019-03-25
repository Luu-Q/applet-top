package com.applet.model.log;

import lombok.Data;

@Data
public class OutGoingLog {
    private Integer id;
    private String request_id;
    private String opt_type;
    private String request_url;
    private String header_message;
    private String request_message;
    private String response_message;
    private String exception_msg;
    private Long run_time;
    private Integer operation_type;
}
