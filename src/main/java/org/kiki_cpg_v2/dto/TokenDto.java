/**
 * @DaSep 10, 2020 @TokenDto.java
 */
package org.kiki_cpg_v2.dto;

/**
 * @author Anjana Thrishakya
 */
public class TokenDto {

	private String token;
	private String responseCode;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	@Override
	public String toString() {
		return "TokenDto [token=" + token + ", responseCode=" + responseCode + "]";
	}

}
