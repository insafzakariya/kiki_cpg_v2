package org.kiki_cpg_v2.dto;

public class SmsDto {

	private String message;
	private String mobile;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return "SmsDto [message=" + message + ", mobile=" + mobile + "]";
	}

}
