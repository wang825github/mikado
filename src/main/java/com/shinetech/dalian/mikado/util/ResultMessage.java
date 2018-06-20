package com.shinetech.dalian.mikado.util;

/**
 * @author  Justin
 *
 */
public class ResultMessage {
	private String message;
	private Boolean success;
	
	public ResultMessage(String message) {
		super();
		this.message = message;
	}
	
	public ResultMessage(String message, Boolean success) {
		super();
		this.message = message;
		this.success = success;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	
}
