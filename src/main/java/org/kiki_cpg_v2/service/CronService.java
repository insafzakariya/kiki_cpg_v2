package org.kiki_cpg_v2.service;

import org.kiki_cpg_v2.entity.CronReportEntity;

public interface CronService {

	void startDialogCron(String cronName,String ipAddress, String date, String time);

	void startMobitelCron(String cronName,String ipAddress, String date, String time);

	CronReportEntity saveCron(String cronName, String ipAddress, String date, String time, String type);

	CronReportEntity saveCron(CronReportEntity cronReportEntity);

	void cronErrorSave(Integer cronId, String errorDesc, String errorMsg, String systemPage, Integer viewerId);

	/**
	 * @param name
	 * @param ipAddress
	 * @param date
	 * @param time
	 */
	void startHnbCron(String name, String ipAddress, String date, String time);

}
