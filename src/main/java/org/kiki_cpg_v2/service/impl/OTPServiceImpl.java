/**
 * @DaJul 23, 2020 @OTPServiceImpl.java
 */
package org.kiki_cpg_v2.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

import org.kiki_cpg_v2.dto.DialogOtpDto;
import org.kiki_cpg_v2.dto.ResponseMapDto;
import org.kiki_cpg_v2.dto.request.DialogOtpConfirmDto;
import org.kiki_cpg_v2.entity.CKOTPEntity;
import org.kiki_cpg_v2.entity.ViewerEntity;
import org.kiki_cpg_v2.repository.CKOTPRepository;
import org.kiki_cpg_v2.repository.ViewerRepository;
import org.kiki_cpg_v2.service.OTPService;
import org.kiki_cpg_v2.service.SmsService;
import org.kiki_cpg_v2.service.SubscriptionPaymentService;
import org.kiki_cpg_v2.util.AppConstant;
import org.kiki_cpg_v2.util.AppUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Anjana Thrishakya
 */
@Service
public class OTPServiceImpl implements OTPService{
	
	@Autowired
	private ViewerRepository viewerRepository;
	
	@Autowired
	private SmsService smsService;
	
	@Autowired
	private CKOTPRepository ckotpRepository;
	
	@Autowired
	private AppUtility appUtility;
	
	@Autowired
	private SubscriptionPaymentService subscriptionService;

	@Override
	public DialogOtpDto sendOtp(Integer viewer_id, String mobileNo) throws Exception {
		DialogOtpDto dialogOtpDto = new DialogOtpDto();
		ViewerEntity viewerEntity = viewerRepository.findById(viewer_id).get();
		if(viewerEntity != null) {
			String otp = genarateOTP(mobileNo);
			if(!otp.equalsIgnoreCase("error")) {
				CKOTPEntity ckotpEntity = getCKOTPEntity(viewer_id, "0"+appUtility.getNineDigitMobileNumber(mobileNo), otp);
				
				if(ckotpRepository.save(ckotpEntity)!= null) {
					dialogOtpDto.setStatus("success");
					dialogOtpDto.setMessage("success");
				};
			} else {
				dialogOtpDto.setStatus("fail");
				dialogOtpDto.setMessage("error");
			}
		} else {
			dialogOtpDto.setStatus("fail");
			dialogOtpDto.setMessage("Viewer Not Found");
		}
		
		System.out.println(dialogOtpDto.toString());
		
		return dialogOtpDto;
		
	}

	
	/**
	 * @param viewer_id
	 * @param mobileNo
	 * @param otp
	 * @return
	 */
	public CKOTPEntity getCKOTPEntity(Integer viewer_id, String mobileNo, String otp) {
		Date date= new Date();
        long time = date.getTime();
        Timestamp ts_start = new Timestamp(time);
        Timestamp ts_expire = new Timestamp(time+ 5*60000);
        
		CKOTPEntity entity = new CKOTPEntity();
		entity.setCode(otp);
		entity.setCreatedDate(ts_start);
		entity.setMobile(mobileNo);
		entity.setViwerId(viewer_id);
		entity.setExpireDate(ts_expire);
		return entity;
	}


	public String genarateOTP(String mobile_no) throws Exception {
		Random rand = new Random();
		String code = String.format("%06d", rand.nextInt(999999));
		Integer resultCode = smsService.sendSms(mobile_no, "Dear Customer, please use the following OTP " +code + " to complete your kiki verification.");
		System.out.println("otp : " + code);
		//Integer resultCode = 200;
		if (resultCode==200) {
			return code;
		}else {
			return "error";
		}
		
	}


	@Override
	public ResponseMapDto confirm(DialogOtpConfirmDto dialogOtpConfirmDto) throws Exception {
		boolean isValied = subscriptionService.validateSubscriptionPayment(dialogOtpConfirmDto.getSubscriptionPaymentId());
		ResponseMapDto dto = new ResponseMapDto();
		if(isValied) {
			CKOTPEntity ckotpEntity = ckotpRepository.findOneByCodeAndCreatedDateLessThanEqualAndExpireDateGreaterThanEqual(dialogOtpConfirmDto.getPin(), new Date(), new Date());
			if(ckotpEntity != null) {
				dto.setStatus("Success");
			} else {
				dto.setStatus("OTP Wrong or Expired");
			}
		} else {
			dto.setStatus("Subscription Token Expired");
		}
		
		return dto;
	}
}
