package com.kiki_cpg.development.service;

import com.kiki_cpg.development.dto.ResponseDto;

public interface ViewerService {

	public ResponseDto vieweBalance(String mobileNumber);

	public String create_access_token();

	public String genarate_authorization_code();

	public String gearate_Number(String mobileNumber);

}
