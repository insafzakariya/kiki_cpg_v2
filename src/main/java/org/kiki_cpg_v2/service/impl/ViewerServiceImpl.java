package org.kiki_cpg_v2.service.impl;

import org.kiki_cpg_v2.dto.InvoiceDetailsForNotificationEmailDto;
import org.kiki_cpg_v2.entity.ViewerEntity;
import org.kiki_cpg_v2.repository.ViewerRepository;
import org.kiki_cpg_v2.service.EmailService;
import org.kiki_cpg_v2.service.ViewerService;
import org.kiki_cpg_v2.util.AppUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ViewerServiceImpl implements ViewerService {

	@Autowired
	private ViewerRepository viewerRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private AppUtility appUtility;

	@Override
	public ViewerEntity updateViewerMobileNumber(String mobileNo, Integer viewerId) {
		ViewerEntity viewerEntity = viewerRepository.findById(viewerId).get();
		viewerEntity.setMobileNumber("+94" + appUtility.getNineDigitMobileNumber(mobileNo));
		return viewerRepository.save(viewerEntity);
	}

	@Override
	public ViewerEntity updateViewerEmailAddress(String emailAddress, Integer viewerId) {
		ViewerEntity viewerEntity = viewerRepository.findById(viewerId).get();
		viewerEntity.setEmail(emailAddress);
		return viewerRepository.save(viewerEntity);
	}

	@Override
	public ViewerEntity getViewerEmailAddress(Integer viewerId) {
		ViewerEntity viewerEntity = viewerRepository.findById(viewerId).get();
		return viewerEntity;
	}

	@Override
	public boolean sendViewerNotificationEmail(String emailAddress, Integer viewerId, String subjectEmail,
			InvoiceDetailsForNotificationEmailDto invoiceDetailsForNotificationEmailDto) throws Exception {
		boolean isEmailSuccess = false;
		if (invoiceDetailsForNotificationEmailDto != null
				&& invoiceDetailsForNotificationEmailDto.getInvoiceId() != null) {
			String template = getEmailTemplate(10);
			template = template.replaceFirst("#AMOUNT", invoiceDetailsForNotificationEmailDto.getAmount().toString());
			template = template.replaceFirst("#DATE", invoiceDetailsForNotificationEmailDto.getCreateDate().toString());
			isEmailSuccess = emailService.sendSimpleMessage(emailAddress, subjectEmail, template);
			System.out.println("Viewer Notification Email Send Succussfully");
		} else {
			System.out.println("Viewer Notification Email Send Failed");
		}
		return isEmailSuccess;
	}

	private String getEmailTemplate(Integer emailTemplateID) {
		String template = "<div bgcolor=\"#000\"> <div id=\"body-style\" style=\"background-color:#000;color:#fff;font-family:verdana,sans-serif;font-size:15px\"> <table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#000\" style=\"border-spacing:0;margin:0 auto;padding:0;line-height:100%\"> <tbody><tr> <td valign=\"top\" width=\"2%\" style=\"border-collapse:collapse\"></td> <td valign=\"top\" width=\"96%\" style=\"border-collapse:collapse\"><table align=\"center\" width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"\" style=\"border-spacing:0;margin:0 auto;padding:0;line-height:100%\"> <tbody><tr> <td height=\"20\" style=\"border:0;border-collapse:collapse\"></td> </tr> </tbody></table> <table style=\"background:#000 url(https://cdn.kiki.lk/img/E-mail-01[12780].png) no-repeat left top;background-size:cover;border:0;border-bottom:0 none;border-spacing:0;margin:0 auto;padding:0;line-height:100%\" align=\"center\" width=\"600\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#000\"> <tbody><tr> <td height=\"40\" style=\"border-collapse:collapse\"></td> </tr> <tr> <td style=\"color:#000;font-size:0;line-height:1;text-align:center;border-collapse:collapse\"></td> </tr> <tr> <td height=\"40\" style=\"border-collapse:collapse\"></td> </tr> <tr> <td valign=\"top\" style=\"border-collapse:collapse\"><table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%!important;border-spacing:0;margin:0 auto;padding:0;line-height:100%\"> <tbody><tr> <td valign=\"top\" width=\"12%\" class=\"m_-1365182277649159425left-column\" style=\"border-collapse:collapse\"></td> <td valign=\"top\" width=\"76%\" class=\"m_-1365182277649159425table-90\" style=\"border-collapse:collapse\"><table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%!important;border-spacing:0;margin:0 auto;padding:0;line-height:100%\"> <tbody><tr> <td height=\"15\" style=\"border-collapse:collapse\"></td> </tr> <tr> <td style=\"color:#fff;font-size:16px;text-align:left;border-collapse:collapse\">Dear Valued Customer,</td> </tr> <tr> <td height=\"20\" style=\"border-collapse:collapse\"></td> </tr> <tr> <td style=\"color:#fff;font-size:16px;line-height:1.4;text-align:left;border-collapse:collapse\">Your payment has been made successfully.</td> </tr> <tr> <td style=\"color:#fff;font-size:16px;line-height:1.4;text-align:left;border-collapse:collapse\">Please find the transaction details below.<br></td> </tr> <tr> <td height=\"20\" style=\"border-collapse:collapse\"></td> </tr> <tr> <td style=\"color:#fff;font-size:16px;line-height:1.4;text-align:left;border-collapse:collapse\">Merchant name - <span class=\"il\">KiKi</span><br></td> </tr> <tr> <td style=\"color:#fff;font-size:16px;line-height:1.4;text-align:left;border-collapse:collapse\">Amount-Rs. #AMOUNT<br></td> </tr> <tr> <td style=\"color:#fff;font-size:16px;line-height:1.4;text-align:left;border-collapse:collapse\">Date - #DATE<br></td> </tr> <tr> <td height=\"40\" style=\"border-collapse:collapse\"></td> </tr> <tr> <td style=\"color:#fff;font-size:16px;text-align:left;border-collapse:collapse\">Thank You!</td> </tr> <tr> <td height=\"10\" style=\"border-collapse:collapse\"></td> </tr> <tr> <td style=\"color:#fff;font-size:16px;text-align:left;border-collapse:collapse\">Team <span class=\"il\">KiKi</span></td> </tr> <tr> <td height=\"40\" style=\"border-collapse:collapse\"></td> </tr> </tbody></table></td> <td valign=\"top\" width=\"12%\" class=\"m_-1365182277649159425right-column\" style=\"border-collapse:collapse\"></td> </tr> </tbody></table></td> </tr> <tr> <td style=\"color:#fff;font-size:14px;text-align:left;line-height:2px;border-collapse:collapse\"><img src=\"https://ci6.googleusercontent.com/proxy/XjZjrCMi9-YHfMoubyIS1ckCdzOWR1cLxVFbS5NlzsuIAyXQSFwWc6Cd9wdZUmoHumrPhT4RdnmXpjJj4P14mlC8tZ0jzlCZX1FR2Wf59yZu29vQq6lE5Ra3j18o1_i7owS2GQ72OM2RdtHRqnd5pNkzRzwlYVtuP0hNhl8=s0-d-e1-ft#https://gallery.mailchimp.com/f1038117f87fdaaa8adbf243f/images/96ee73c7-c6e7-4c64-b3fd-c234dbfeb4f5.gif\" border=\"0\" alt=\"ALT Media\" style=\"display:block;height:2px;width:100%!important;outline:none;text-decoration:none;max-width:100%\" class=\"CToWUd\"></td> </tr> <tr> <td valign=\"top\" style=\"border-collapse:collapse\"><table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%!important;border-spacing:0;margin:0 auto;padding:0;line-height:100%\"> <tbody><tr> <td valign=\"top\" width=\"12%\" class=\"m_-1365182277649159425left-column\" style=\"border-collapse:collapse\"></td> <td valign=\"top\" width=\"76%\" class=\"m_-1365182277649159425table-90\" style=\"border-collapse:collapse\"><table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%!important;border-spacing:0;margin:0 auto;padding:0;line-height:100%\"> <tbody><tr> <td height=\"15\" style=\"border-collapse:collapse\"></td> </tr> <tr> <td class=\"m_-1365182277649159425mobile-height\" valign=\"middle\" style=\"color:#fff;font-size:11px;text-align:left;border-collapse:collapse\">Question? email <a href=\"mailto:care@kiki.lk\" style=\"color:#fff;outline:none;text-decoration:underline\" target=\"_blank\">care@<span class=\"il\">kiki</span>.lk</a></td> </tr> <tr> <td height=\"15\" style=\"border-collapse:collapse\"></td> </tr> <tr> <td valign=\"middle\" style=\"text-align:left;border-collapse:collapse\"><table align=\"left\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:150px!important;border-spacing:0;margin:0 auto;padding:0;line-height:100%\"> <tbody><tr> <td width=\"10px;\" align=\"left\" style=\"border-collapse:collapse\"><a href=\"https://www.facebook.com/mv.kiki.lk\" target=\"_blank\" ><img src=\"https://ci4.googleusercontent.com/proxy/nxEdFTZuLZVqr2QhLxp-uEunpzjgeckquJHtaloDLGEdeiw2q4q-nvWMyAY2cXyuRiSSU1gCBJ39Ypy9qy-oqUGYxXbUYaG2snoWMja1WMJROLodusIPcI48OlbjeDJoKWTDIKTnVFui5Lfvs5VoCMYxu8t5EMhMkJ_DMow=s0-d-e1-ft#https://gallery.mailchimp.com/f1038117f87fdaaa8adbf243f/images/712b3aa2-cac2-4f67-a42b-96f89acbd12f.png\" border=\"0\" alt=\"Facebook\" style=\"display:block;outline:none;text-decoration:none;height:auto;max-width:100%;border:none\" class=\"CToWUd\"></a></td> <td width=\"15px;\" align=\"left\" style=\"border-collapse:collapse\"><a href=\"https://www.youtube.com/channel/UCwDZAhU6AORz1XSOM1M_uOQ\" target=\"_blank\"><img src=\"https://ci6.googleusercontent.com/proxy/yIZ5XldI6eIdt1nfGcQE58Sjbrg9MrGCd01rapV9uZf-BPTh9pA7gYsrkmAVrVh_ttDCf0c01xCUH4QAO6PmzB4CLec2QQlPiRm1opUzh6gh4ts9QFF9uHpqOlCRK3kJaH7NrFAsAZNI6Nfh5v8h4PTGqaDCtKD2t07Qin4=s0-d-e1-ft#https://gallery.mailchimp.com/f1038117f87fdaaa8adbf243f/images/5b05038c-d87c-4eba-93f6-75c6a3181962.png\" border=\"0\" alt=\"Youtube\" style=\"display:block;outline:none;text-decoration:none;height:auto;max-width:100%;border:none\" class=\"CToWUd\"></a></td> </tr> </tbody></table></td> </tr> <tr> <td height=\"15\" style=\"border-collapse:collapse\"></td> </tr> </tbody></table></td> <td valign=\"top\" width=\"12%\" class=\"m_-1365182277649159425right-column\" style=\"border-collapse:collapse\"></td> </tr> <tr> <td height=\"40\" colspan=\"3\" style=\"border-collapse:collapse\"></td> </tr> </tbody></table></td> </tr> </tbody></table> <table align=\"center\" width=\"600\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-spacing:0;margin:0 auto;padding:0;line-height:100%\"> <tbody><tr> <td height=\"20\" style=\"border-collapse:collapse\"></td> </tr> </tbody></table></td> <td valign=\"top\" width=\"2%\" style=\"border-collapse:collapse\"></td> </tr> </tbody></table> </div> <img src=\"../../dist/img/E-mail-01[12780].png\" height=\"1\" width=\"1\" class=\"CToWUd\"></div><div class=\"yj6qo\"></div><div class=\"adL\"> </div></div></div><div id=\":2lh\" class=\"ii gt\" style=\"display:none\"><div id=\":2jz\" class=\"a3s aXjCH undefined\"></div></div><div class=\"hi\"></div></div></div><div class=\"ajx\"></div></div></div></div></div></div></div>";
		return template;
	}

}
