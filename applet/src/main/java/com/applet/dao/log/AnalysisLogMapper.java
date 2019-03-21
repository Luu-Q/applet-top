package com.applet.dao.log;


import com.applet.entity.log.AnalysisLog;
import com.applet.model.log.OutGoingLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AnalysisLogMapper {


    @Insert("insert into clean_analysis_log (request_ip, log_type, operation_type, action_method, request_params, response_msg, action_date, run_time, description)"
            +
            "value (#{t.request_ip}, #{t.log_type}, #{t.operation_type}, #{t.action_method}, #{t.request_params}, #{t.response_msg}, #{t.action_date}, #{t.run_time}, #{t.description})")
    int saveAnalysisLog(@Param("t") AnalysisLog systemLog);

    @Insert("insert into clean_outgoing_log (request_id, opt_type, request_url, header_message, request_message, response_message, exception_msg, run_time, operation_type) "
            +
            "value (#{t.request_id}, #{t.opt_type}, #{t.request_url}, #{t.header_message}, #{t.request_message}, #{t.response_message}, #{t.exception_msg}, #{t.run_time}, #{t.operation_type})")
    int saveOutGoingLog(@Param("t") OutGoingLog goingLog);

}
