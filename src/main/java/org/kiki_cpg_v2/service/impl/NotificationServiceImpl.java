/**
 * @DaJun 25, 2020 @NotificationServiceImpl.java
 */
package org.kiki_cpg_v2.service.impl;

import org.kiki_cpg_v2.client.NotificationClient;
import org.kiki_cpg_v2.dto.NotificationDto;
import org.kiki_cpg_v2.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Anjana Thrishakya
 */
@Service
@Transactional
public class NotificationServiceImpl implements NotificationService{

	@Autowired
	private NotificationClient notificationClient;
	
	@Override
	public String sendNotification(NotificationDto notificationDto) throws Exception {
		return notificationClient.sendNotification(notificationDto);
	}

}
