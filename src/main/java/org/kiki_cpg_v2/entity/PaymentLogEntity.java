package org.kiki_cpg_v2.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payment_log")
public class PaymentLogEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "viwerId")
	private Integer viwerId;
	
	@Column(name = "mobileNo")
	private String mobileNo;
	
	@Column(name = "responseNo")
	private String responseNo;
	
	@Column(name = "responseMsg")
	private String responseMsg;
	
	@Column(name = "serviceProvider")
	private String serviceProvider;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "serverResponse")
	private String serverResponse;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getViwerId() {
		return viwerId;
	}

	public void setViwerId(Integer viwerId) {
		this.viwerId = viwerId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getResponseNo() {
		return responseNo;
	}

	public void setResponseNo(String responseNo) {
		this.responseNo = responseNo;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public String getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getServerResponse() {
		return serverResponse;
	}

	public void setServerResponse(String serverResponse) {
		this.serverResponse = serverResponse;
	}

}
