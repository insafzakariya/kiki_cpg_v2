package org.kiki_cpg_v2.service;

import org.kiki_cpg_v2.entity.CronViewerReportEntity;

public interface CronViewerRepostService {

	boolean save(Integer viwerId, String status, Double amount, String respMessage, String serverResponse, Integer cronId);

	CronViewerReportEntity getCronViewerReportEntity(Integer viwerId, String status, Double amount, String respMessage,
			String serverResponse, Integer cronId);

}
