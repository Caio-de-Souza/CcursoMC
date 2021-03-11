package com.souza.caio.resources.exceptions;

import java.io.Serializable;

public class StandardError implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer status;
	private String msgm;
	private Long timeStamp;
		
	public StandardError(Integer status, String msgm, Long timeStamp) {
		super();
		this.status = status;
		this.msgm = msgm;
		this.timeStamp = timeStamp;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMsgm() {
		return msgm;
	}
	public void setMsgm(String msgm) {
		this.msgm = msgm;
	}
	public Long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
}
