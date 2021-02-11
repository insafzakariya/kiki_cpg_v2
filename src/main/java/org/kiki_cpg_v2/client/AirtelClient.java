/**
 * @DaFeb 5, 2021 @AirtelClient.java
 */
package org.kiki_cpg_v2.client;

import org.kiki_cpg_v2.dto.AirtelClientResponseDto;
import org.kiki_cpg_v2.dto.request.AirtelClientRequestDto;

/**
 * @author Anjana Thrishakya
 */
public interface AirtelClient {
	
	/**
	 * @param airtelClientRequestDto
	 * @return
	 */
	AirtelClientResponseDto subscribe(AirtelClientRequestDto airtelClientRequestDto);

}
