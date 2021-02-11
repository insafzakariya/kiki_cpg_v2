/**
 * @DaFeb 5, 2021 @AirtelClientRequestDto.java
 */
package org.kiki_cpg_v2.dto.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * @author Anjana Thrishakya
 */
@JacksonXmlRootElement(localName = "ocsRequest")
public class AirtelClientRequestDto {

	private Integer cpcgFlag = 10;
	private Integer requestType = 20;
	private String serviceNode = "aa";
	private String sequenceNo;
	private String callingParty;
	private String calledParty;
	private String serviceType;
	private String serviceId;
	private String planId;
	private String renFlag;
	private Long startTime;
	private String subscrFlag;
	private String bearerId;
	private String asyncFlag;
	private String reqSource;
	private String contentId;
	private String category;
	private String regionId;
	private String languageId;
	private String OptionalParameter1;
	private String OptionalParameter2;
	private String OptionalParameter3;
	private String OptionalParameter4;
	private String OptionalParameter5;

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

	public String getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getCallingParty() {
		return callingParty;
	}

	public void setCallingParty(String callingParty) {
		this.callingParty = callingParty;
	}

	public String getCalledParty() {
		return calledParty;
	}

	public void setCalledParty(String calledParty) {
		this.calledParty = calledParty;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getRenFlag() {
		return renFlag;
	}

	public void setRenFlag(String renFlag) {
		this.renFlag = renFlag;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public String getSubscrFlag() {
		return subscrFlag;
	}

	public void setSubscrFlag(String subscrFlag) {
		this.subscrFlag = subscrFlag;
	}

	public String getBearerId() {
		return bearerId;
	}

	public void setBearerId(String bearerId) {
		this.bearerId = bearerId;
	}

	public String getAsyncFlag() {
		return asyncFlag;
	}

	public void setAsyncFlag(String asyncFlag) {
		this.asyncFlag = asyncFlag;
	}

	public String getReqSource() {
		return reqSource;
	}

	public void setReqSource(String reqSource) {
		this.reqSource = reqSource;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getLanguageId() {
		return languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}

	public String getOptionalParameter1() {
		return OptionalParameter1;
	}

	public void setOptionalParameter1(String optionalParameter1) {
		OptionalParameter1 = optionalParameter1;
	}

	public String getOptionalParameter2() {
		return OptionalParameter2;
	}

	public void setOptionalParameter2(String optionalParameter2) {
		OptionalParameter2 = optionalParameter2;
	}

	public String getOptionalParameter3() {
		return OptionalParameter3;
	}

	public void setOptionalParameter3(String optionalParameter3) {
		OptionalParameter3 = optionalParameter3;
	}

	public String getOptionalParameter4() {
		return OptionalParameter4;
	}

	public void setOptionalParameter4(String optionalParameter4) {
		OptionalParameter4 = optionalParameter4;
	}

	public String getOptionalParameter5() {
		return OptionalParameter5;
	}

	public void setOptionalParameter5(String optionalParameter5) {
		OptionalParameter5 = optionalParameter5;
	}

}
