/**
 * @DaSep 23, 2020 @IDGeneratorServiceImpl.java
 */
package org.kiki_cpg_v2.service.impl;

import java.util.Date;

import org.kiki_cpg_v2.entity.IdGeneratorEntity;
import org.kiki_cpg_v2.repository.IdGeneratorRepository;
import org.kiki_cpg_v2.service.IDGeneratorService;
import org.kiki_cpg_v2.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Anjana Thrishakya
 */
@Service
@Transactional
public class IDGeneratorServiceImpl implements IDGeneratorService {

	@Autowired
	private IdGeneratorRepository idGeneratorRepository;
	@Override
	public Integer generateId(String type) throws Exception {
		IdGeneratorEntity idGeneratorEntity = idGeneratorRepository.findFirstByTypeAndStatus(type, AppConstant.ACTIVE);
		if(idGeneratorEntity != null) {
			Integer sequenceId = idGeneratorEntity.getCurrentSequence() + idGeneratorEntity.getIncrement();
			System.out.println("sequenceId : " + sequenceId );
			idGeneratorEntity.setCurrentSequence(sequenceId);
			idGeneratorEntity.setUpdateDate(new Date());
			if(idGeneratorRepository.save(idGeneratorEntity) != null) {
				System.out.println("sequenceId : " + sequenceId );
				return sequenceId;
			} else {
				return -1;
			}
		} else {
			return -1;
		}
	}

}
