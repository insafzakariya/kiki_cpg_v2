package org.kiki_cpg_v2.dto;

public class DialogOtpDto {
	private String statusCode;
	private String serverRef;
	private String msisdn;
	private String serviceId;
	private String status;
	private String result;
	private String transactionOperationStatus;
	private String message;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getServerRef() {
		return serverRef;
	}

	public void setServerRef(String serverRef) {
		this.serverRef = serverRef;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getTransactionOperationStatus() {
		return transactionOperationStatus;
	}

	public void setTransactionOperationStatus(String transactionOperationStatus) {
		this.transactionOperationStatus = transactionOperationStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "DialogOtpDto [statusCode=" + statusCode + ", serverRef=" + serverRef + ", msisdn=" + msisdn
				+ ", serviceId=" + serviceId + ", status=" + status + ", result=" + result
				+ ", transactionOperationStatus=" + transactionOperationStatus + ", message=" + message + "]";
	}
}
