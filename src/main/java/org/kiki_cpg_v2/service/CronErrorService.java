package org.kiki_cpg_v2.service;

import org.kiki_cpg_v2.entity.CronErrorEntity;

public interface CronErrorService {

	void create(Integer viewerId, String description, String message, String systemPage);

	CronErrorEntity getCronErrorEntity(Integer viewerId, String description, String message, String systemPage);

}
