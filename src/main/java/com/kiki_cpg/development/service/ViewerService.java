package com.kiki_cpg.development.service;

import com.kiki_cpg.development.dto.ResponseDto;
import com.kiki_cpg.development.entity.ViewerPackages;
import com.kiki_cpg.development.entity.Viewers;

public interface ViewerService {

	public ResponseDto vieweBalance(String mobileNumber);

	//public String create_access_token();

	public String genarate_authorization_code();

	public String gearate_Number(String mobileNumber);

	public Viewers getViewerByid(int viewerID);

	public Viewers updateViewerMobileNumber(String mobile_no, Integer viewerId);


	

	

}
