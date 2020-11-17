/**
 * @author Anjana Thrishakya
 * Nov 12, 2020
 * 6:58:43 AM
 */
package org.kiki_cpg_v2.service.impl;

import java.util.Date;

import org.kiki_cpg_v2.entity.ViewerNotificationEntity;
import org.kiki_cpg_v2.repository.ViewerNotificationRepository;
import org.kiki_cpg_v2.service.ViewerNotificationService;
import org.kiki_cpg_v2.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
@Transactional
public class ViewerNotificationServiceImpl implements ViewerNotificationService{

	@Autowired
	private ViewerNotificationRepository viewerNotificationRepository;
	
	@Override
	public boolean save(String message, Integer viewerId) throws Exception {
		ViewerNotificationEntity viewerNotificationEntity = new ViewerNotificationEntity();
		viewerNotificationEntity.setContentType(2);
		viewerNotificationEntity.setMessageContent(message);
		viewerNotificationEntity.setMessageTime(new Date());
		viewerNotificationEntity.setStatus(AppConstant.ACTIVE);
		viewerNotificationEntity.setViewerId(viewerId);
		
		if (viewerNotificationRepository.save(viewerNotificationEntity) != null) {
			return true;
		}
		
		return false;
	}

}
