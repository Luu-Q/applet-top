package com.applet.entity.log;
import org.apache.commons.lang3.StringUtils;

public class OgLog {
    private OptType optType;
    private String requestId = StringUtils.EMPTY;
    private String url = StringUtils.EMPTY;
    private Object headerMessage;
    private Object requestMessage;
    private Object responseMessage;
    private String error = StringUtils.EMPTY;
    private long runtime;
    
    
    public static OgLog getOgLog(String requestId, OptType optType, String url, Object headerMessage,
                                 Object requestMessage, Object responseMessage, String error, long runtime) {
        
        OgLog ogLog = new OgLog();
		ogLog.setOptType(optType);
		ogLog.setRequestId(requestId);
        ogLog.setUrl(url);
        ogLog.setHeaderMessage(headerMessage);
        ogLog.setRequestMessage(requestMessage);
        ogLog.setResponseMessage(responseMessage);
        ogLog.setError(error);
        ogLog.setRuntime(runtime);
        
        return ogLog;
    }


	public OptType getOptType() {
		return optType;
	}


	public void setOptType(OptType optType) {
		this.optType = optType;
	}


	public String getRequestId() {
		return requestId;
	}


	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public Object getHeaderMessage() {
		return headerMessage;
	}


	public void setHeaderMessage(Object headerMessage) {
		this.headerMessage = headerMessage;
	}


	public Object getRequestMessage() {
		return requestMessage;
	}


	public void setRequestMessage(Object requestMessage) {
		this.requestMessage = requestMessage;
	}


	public Object getResponseMessage() {
		return responseMessage;
	}


	public void setResponseMessage(Object responseMessage) {
		this.responseMessage = responseMessage;
	}


	public String getError() {
		return error;
	}


	public void setError(String error) {
		this.error = error;
	}


	public long getRuntime() {
		return runtime;
	}


	public void setRuntime(long runtime) {
		this.runtime = runtime;
	}
    
}
