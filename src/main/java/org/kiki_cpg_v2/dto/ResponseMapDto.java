package org.kiki_cpg_v2.dto;

import java.util.HashMap;

public class ResponseMapDto {
	
	

	private String status;
	private HashMap<String, Object> dataMap;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public HashMap<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(HashMap<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
	@Override
	public String toString() {
		return "ResponseMapDto [status=" + status + ", dataMap=" + dataMap + "]";
	}
}
