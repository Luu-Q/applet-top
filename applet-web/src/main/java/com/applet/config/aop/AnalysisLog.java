package com.applet.config.aop;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class AnalysisLog {

    private Object requestContent;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object responseContent;
    private long runtime;
    
}
