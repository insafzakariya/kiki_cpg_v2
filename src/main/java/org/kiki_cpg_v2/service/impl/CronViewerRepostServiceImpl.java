package org.kiki_cpg_v2.service.impl;

import java.util.Date;

import org.kiki_cpg_v2.entity.CronViewerReportEntity;
import org.kiki_cpg_v2.repository.CronViewerReportRepository;
import org.kiki_cpg_v2.service.CronViewerRepostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CronViewerRepostServiceImpl implements CronViewerRepostService{
	
	@Autowired
	private CronViewerReportRepository cronViewerReportRepository;

	@Override
	public boolean save(Integer viwerId, String status, Double amount, String respMessage, String serverResponse, Integer cronId) {
		if(cronViewerReportRepository.save(getCronViewerReportEntity(viwerId, status, amount, respMessage, serverResponse, cronId))!= null) {
			return true;
		}
		return false;
	}

	@Override
	public CronViewerReportEntity getCronViewerReportEntity(Integer viwerId, String status, Double amount, String respMessage, String serverResponse, Integer cronId) {
		CronViewerReportEntity entity = new CronViewerReportEntity();
		entity.setChargeAmount(amount);
		entity.setCronId(cronId);
		entity.setResponseDateAndTime(new Date());
		entity.setResponseMsg(respMessage);
		entity.setServerResponse(serverResponse);
		entity.setStatus(status);
		entity.setViewerId(viwerId);
		
		return entity;
		
	}
	
}
