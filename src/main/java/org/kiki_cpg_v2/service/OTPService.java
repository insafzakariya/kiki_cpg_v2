/**
 * @DaJul 23, 2020 @OTPService.java
 */
package org.kiki_cpg_v2.service;

import org.kiki_cpg_v2.dto.DialogOtpDto;
import org.kiki_cpg_v2.dto.ResponseMapDto;
import org.kiki_cpg_v2.dto.request.DialogOtpConfirmDto;

/**
 * @author Anjana Thrishakya
 */
public interface OTPService {

	/**
	 * @param viewer_id
	 * @return
	 */
	DialogOtpDto sendOtp(Integer viewer_id, String mobileNo) throws Exception;

	/**
	 * @param dialogOtpConfirmDto
	 * @throws Exception 
	 */
	ResponseMapDto confirm(DialogOtpConfirmDto dialogOtpConfirmDto) throws Exception;

}
