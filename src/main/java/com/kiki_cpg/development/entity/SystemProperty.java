package com.kiki_cpg.development.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "system_property")
public class SystemProperty {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PID")
	private Integer PID;
	
	@Column(name = "KeyValue")
	private String KeyValue;
	
	@Column(name = "Value")
	private String Value;

	public Integer getPID() {
		return PID;
	}

	public void setPID(Integer pID) {
		PID = pID;
	}

	public String getKeyValue() {
		return KeyValue;
	}

	public void setKeyValue(String keyValue) {
		KeyValue = keyValue;
	}

	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}
	
	
}
