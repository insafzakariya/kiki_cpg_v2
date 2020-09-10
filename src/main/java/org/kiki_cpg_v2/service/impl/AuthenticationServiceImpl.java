/**
 * @DaSep 10, 2020 @AuthenticationServiceImpl.java
 */
package org.kiki_cpg_v2.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.kiki_cpg_v2.dto.TokenDto;
import org.kiki_cpg_v2.dto.request.TokenRequestDto;
import org.kiki_cpg_v2.entity.PackageConfigEntity;
import org.kiki_cpg_v2.entity.SubscriptionPaymentEntity;
import org.kiki_cpg_v2.repository.PackageConfigRepository;
import org.kiki_cpg_v2.repository.SubscriptionPaymentRepository;
import org.kiki_cpg_v2.service.AuthenticationService;
import org.kiki_cpg_v2.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Anjana Thrishakya
 */
@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService{
	
	@Autowired
	private PackageConfigRepository packageConfigRepository;
	
	@Autowired
	private SubscriptionPaymentRepository subscriptionPaymentRepository;

	@Override
	public TokenDto generateToken(TokenRequestDto tokenRquestDto) throws Exception {
		PackageConfigEntity packageConfigEntity = packageConfigRepository.findFirstByType(tokenRquestDto.getType());
		if(packageConfigEntity != null) {
			SubscriptionPaymentEntity entity = getSubscriptionPaymentEntity(packageConfigEntity, tokenRquestDto);
			
			if(subscriptionPaymentRepository.save(entity) != null) {
				return getTokenDTO(entity);
			} else {
				throw new RuntimeException("Login Not Saved");
			}
		} else {
			throw new NullPointerException("Package Not Found");
		}
		
	}

	@Override
	public TokenDto getTokenDTO(SubscriptionPaymentEntity entity) {
		TokenDto tokenDto = new TokenDto();
		tokenDto.setResponseCode("Success");
		tokenDto.setToken(entity.getTokenHash());
		return tokenDto;
	}

	@Override
	public SubscriptionPaymentEntity getSubscriptionPaymentEntity(PackageConfigEntity packageConfigEntity,
			TokenRequestDto tokenRquestDto) throws Exception {
	
		
		SubscriptionPaymentEntity entity = new SubscriptionPaymentEntity();
		entity.setCreatedDate(new Date());
		
		Calendar c = Calendar.getInstance();
		c.setTime(entity.getCreatedDate());
		c.add(Calendar.MINUTE, 10);
		
		entity.setExpireDate( c.getTime());
		entity.setPackageID(packageConfigEntity.getPackageId());
		entity.setStatus(AppConstant.ACTIVE);
		entity.setTokenHash(UUID.randomUUID().toString());
		entity.setViewerID(tokenRquestDto.getViewerId());
		
		return entity;
	}

}
