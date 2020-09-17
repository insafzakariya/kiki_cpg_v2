package org.kiki_cpg_v2.service.impl;

import java.util.Date;

import org.kiki_cpg_v2.entity.CronErrorEntity;
import org.kiki_cpg_v2.repository.CronErrorRepository;
import org.kiki_cpg_v2.service.CronErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CronErrorServiceImpl implements CronErrorService {

	@Autowired
	private CronErrorRepository cronErrorRepository;

	@Override
	public void create(Integer viewerId, String description, String message, String systemPage) {

		CronErrorEntity cronErrorEntity = getCronErrorEntity(viewerId, description, message, systemPage);

		cronErrorRepository.save(cronErrorEntity);

	}

	@Override
	public CronErrorEntity getCronErrorEntity(Integer viewerId, String description, String message, String systemPage) {
		CronErrorEntity entity = new CronErrorEntity();
		entity.setCronId(-1);
		entity.setErrorDate(new Date());
		entity.setErrorDesc(description);
		entity.setErrorMsg(message);
		entity.setSystemPage(systemPage);
		entity.setViewerId(viewerId);

		return entity;
	}

}
