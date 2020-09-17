/**
 * @DaSep 10, 2020 @AuthenticationService.java
 */
package org.kiki_cpg_v2.service;

import org.kiki_cpg_v2.dto.TokenDto;
import org.kiki_cpg_v2.dto.request.TokenRequestDto;
import org.kiki_cpg_v2.entity.PackageConfigEntity;
import org.kiki_cpg_v2.entity.SubscriptionPaymentEntity;

/**
 * @author Anjana Thrishakya
 */
public interface AuthenticationService {

	/**
	 * @param tokenRquestDto
	 * @return
	 */
	TokenDto generateToken(TokenRequestDto tokenRquestDto) throws Exception;

	/**
	 * @param packageConfigEntity
	 * @param tokenRquestDto
	 * @return
	 */
	SubscriptionPaymentEntity getSubscriptionPaymentEntity(PackageConfigEntity packageConfigEntity,
			TokenRequestDto tokenRquestDto) throws Exception;

	/**
	 * @param entity
	 * @return
	 */
	TokenDto getTokenDTO(SubscriptionPaymentEntity entity);

}
