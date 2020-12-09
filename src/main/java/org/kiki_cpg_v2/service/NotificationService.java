/**
 * @DaJun 25, 2020 @NotificationService.java
 */
package org.kiki_cpg_v2.service;

import org.kiki_cpg_v2.dto.NotificationDto;

/**
 * @author Anjana Thrishakya
 */
public interface NotificationService {
	
	String sendNotification(NotificationDto notificationDto) throws Exception;
	
	void sendSubscriptionNotification(String message, String deviceId) throws Exception;

}
