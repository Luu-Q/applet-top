package com.applet.service.log;

import com.alibaba.fastjson.JSON;
import com.applet.entity.log.AnalysisLog;
import com.applet.dao.log.AnalysisLogMapper;
import com.applet.entity.log.OgLog;
import com.applet.model.log.OutGoingLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class AnalysisLogService {

    @Autowired
    private AnalysisLogMapper analysisLogMapper;

    @Async
    public void saveAnalysisLog(AnalysisLog systemLog) {
        try {
            analysisLogMapper.saveAnalysisLog(systemLog);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("saveAnalysisLog方法异常:" + e.toString());
        }
    }

    @Async
    public void saveOutGoingLog(OgLog logInfo) {
        OutGoingLog goingLog = new OutGoingLog();
        try {
            goingLog.setRequest_id(logInfo.getRequestId());
            goingLog.setOpt_type(String.valueOf(logInfo.getOptType().toValue()));
            goingLog.setException_msg(logInfo.getError());
            goingLog.setHeader_message(JSON.toJSONString(logInfo.getHeaderMessage()));
            goingLog.setOperation_type(StringUtils.isNotEmpty(logInfo.getError()) ? 2 : 1);
            goingLog.setRequest_url(logInfo.getUrl());
            goingLog.setRequest_message(JSON.toJSONString(logInfo.getRequestMessage()));
            goingLog.setResponse_message(JSON.toJSONString(logInfo.getResponseMessage()));
            goingLog.setRun_time(logInfo.getRuntime());
            analysisLogMapper.saveOutGoingLog(goingLog);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("saveOutGoingLog方法异常:" + e.toString());
        }
    }



}
