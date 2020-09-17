/**
 * @DaSep 7, 2020 @HutchClient.java
 */
package org.kiki_cpg_v2.client;

import org.kiki_cpg_v2.dto.HutchResponseDto;
import org.kiki_cpg_v2.dto.request.HutchSubscribeDto;

/**
 * @author Anjana Thrishakya
 */
public interface HutchClient {

	/**
	 * @param hutchSubscribeDto
	 * @return
	 */
	HutchResponseDto subscribe(HutchSubscribeDto hutchSubscribeDto);

	/**
	 * @param mobile
	 * @param serviceCode
	 * @return
	 */
	HutchResponseDto unsubscribe(String mobile, String serviceCode);

}
