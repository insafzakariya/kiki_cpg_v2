/**
 * @DaJun 25, 2020 @NotificationClient.java
 */
package org.kiki_cpg_v2.client;

import org.kiki_cpg_v2.dto.NotificationDto;

/**
 * @author Anjana Thrishakya
 */
public interface NotificationClient {

	String sendNotification(NotificationDto notificationDto) throws Exception;
	
}
