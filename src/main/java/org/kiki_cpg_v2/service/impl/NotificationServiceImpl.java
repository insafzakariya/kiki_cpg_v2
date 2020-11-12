/**
 * @DaJun 25, 2020 @NotificationServiceImpl.java
 */
package org.kiki_cpg_v2.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

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

	@Override
	public void sendSubscriptionNotification(String message, String deviceId) throws Exception {
		Thread thread = new Thread() {
			public void run() {
				
				String title = "Subscription";
				
				NotificationDto notificationDto = new NotificationDto();
				notificationDto.getDeviceid().add(deviceId);
				notificationDto.setBody(message);
				notificationDto.setTitle(title);
				notificationDto.setType("2");
				notificationDto.setDate_time(
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(new Date()));
				System.out.println(notificationDto.toString());
				try {
					sendNotification(notificationDto);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		thread.start();
	}
	
	

}
