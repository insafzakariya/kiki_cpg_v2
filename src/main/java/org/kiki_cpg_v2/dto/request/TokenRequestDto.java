/**
 * @DaSep 10, 2020 @TokenRequestDto.java
 */
package org.kiki_cpg_v2.dto.request;

/**
 * @author Anjana Thrishakya
 */
public class TokenRequestDto {
	
	private Integer viewerId;
	private String type;

	public Integer getViewerId() {
		return viewerId;
	}

	public void setViewerId(Integer viewerId) {
		this.viewerId = viewerId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "TokenRequestDto [viewerId=" + viewerId + ", type=" + type + "]";
	}

}
