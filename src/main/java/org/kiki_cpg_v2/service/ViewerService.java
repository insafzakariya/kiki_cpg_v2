package org.kiki_cpg_v2.service;

import org.kiki_cpg_v2.dto.InvoiceDetailsForNotificationEmailDto;
import org.kiki_cpg_v2.entity.ViewerEntity;

public interface ViewerService {

	ViewerEntity updateViewerMobileNumber(String mobileNo, Integer viewerId);
	
	ViewerEntity updateViewerMobileNumberAndTrial(String mobileNo, Integer viewerId, boolean trial);

	ViewerEntity updateViewerEmailAddress(String emailAddress, Integer viewerId);

	ViewerEntity getViewerEmailAddress(Integer viewerId);

	boolean sendViewerNotificationEmail(String emailAddress, Integer viewerId, String subjectEmail,
			InvoiceDetailsForNotificationEmailDto invoiceDetailsForNotificationEmailDto) throws Exception;

}
