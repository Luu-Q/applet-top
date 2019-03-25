package com.applet.entity.log;

import lombok.Data;

@Data
public class AnalysisLog {

    public static long AnalysisDay = 7;
    public static long UserBehaviorDay = 90;

    private Integer id;
    private String request_ip;
    private Integer log_type;
    private Integer operation_type;
    private String action_method;
    private String request_params;
    private String response_msg;
    private Long action_date;
    private Integer run_time;
    private String description;
}
