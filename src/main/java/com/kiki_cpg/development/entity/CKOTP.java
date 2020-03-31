package com.kiki_cpg.development.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "otp")
public class CKOTP {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "viwer_id")
	private Integer viwer_id;

	@Column(name = "code")
	private String code;

	@Column(name = "created_date")
	private Date created_date;

	@Column(name = "expire_date")
	private Date expire_date;

	@Column(name = "subscribe_type")
	private String subscribe_type;

	@Column(name = "mobile")
	private String mobile;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getViwer_id() {
		return viwer_id;
	}

	public void setViwer_id(Integer viwer_id) {
		this.viwer_id = viwer_id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Date getExpire_date() {
		return expire_date;
	}

	public void setExpire_date(Date expire_date) {
		this.expire_date = expire_date;
	}

	public String getSubscribe_type() {
		return subscribe_type;
	}

	public void setSubscribe_type(String subscribe_type) {
		this.subscribe_type = subscribe_type;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
