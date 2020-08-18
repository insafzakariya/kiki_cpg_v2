/**
 * @DaJun 25, 2020 @NotificationClientImpl.java
 */
package org.kiki_cpg_v2.client.impl;

import org.kiki_cpg_v2.client.NotificationClient;
import org.kiki_cpg_v2.dto.NotificationDto;
import org.kiki_cpg_v2.dto.NotificationRespDto;
import org.kiki_cpg_v2.util.AppConstant;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Anjana Thrishakya
 */
@Component
public class NotificationClientImpl implements NotificationClient{

	@Override
	public String sendNotification(NotificationDto notificationDto) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		
		NotificationRespDto resp = restTemplate.postForObject(AppConstant.URL_NOTIFICATION, notificationDto, NotificationRespDto.class);

		
		
		return resp.getNotificationId();
	}

}
