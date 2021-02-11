/**
 * @DaFeb 5, 2021 @AirtelClientResponseDto.java
 */
package org.kiki_cpg_v2.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * @author Anjana Thrishakya
 */
@JacksonXmlRootElement(localName = "ocsResponse")
public class AirtelClientResponseDto {
	private Integer sequenceNo;
	private Integer cpcgFlag;
	private Integer requestType;
	private String serviceNode;
	private String bearerId;
	private String callingParty;
	private String serviceId;
	private String serviceType;
	private String category;
	private String contentId;
	private String planId;
	private String subscrFlag;
	private Double balanceAmount;
	private Double chargeAmount;
	private Double refundAmount;
	private Integer resultCode;
	private String result;

	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public Integer getCpcgFlag() {
		return cpcgFlag;
	}

	public void setCpcgFlag(Integer cpcgFlag) {
		this.cpcgFlag = cpcgFlag;
	}

	public Integer getRequestType() {
		return requestType;
	}

	public void setRequestType(Integer requestType) {
		this.requestType = requestType;
	}

	public String getServiceNode() {
		return serviceNode;
	}

	public void setServiceNode(String serviceNode) {
		this.serviceNode = serviceNode;
	}

	public String getBearerId() {
		return bearerId;
	}

	public void setBearerId(String bearerId) {
		this.bearerId = bearerId;
	}

	public String getCallingParty() {
		return callingParty;
	}

	public void setCallingParty(String callingParty) {
		this.callingParty = callingParty;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getSubscrFlag() {
		return subscrFlag;
	}

	public void setSubscrFlag(String subscrFlag) {
		this.subscrFlag = subscrFlag;
	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Double getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
