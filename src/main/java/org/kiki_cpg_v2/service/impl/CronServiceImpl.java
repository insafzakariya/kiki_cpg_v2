package org.kiki_cpg_v2.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.kiki_cpg_v2.entity.CronErrorEntity;
import org.kiki_cpg_v2.entity.CronReportEntity;
import org.kiki_cpg_v2.entity.custom.IdeabizViewerCusrtomEntity;
import org.kiki_cpg_v2.entity.custom.ViewerSubscriptionCustomEntity;
import org.kiki_cpg_v2.repository.CronErrorRepository;
import org.kiki_cpg_v2.repository.CronMetaDataRepository;
import org.kiki_cpg_v2.repository.CronReportRepository;
import org.kiki_cpg_v2.repository.IdeabizRepository;
import org.kiki_cpg_v2.repository.ViewerSubscriptionRepository;
import org.kiki_cpg_v2.service.CronService;
import org.kiki_cpg_v2.service.IdeabizService;
import org.kiki_cpg_v2.service.MobitelService;
import org.kiki_cpg_v2.service.PaymentMethodService;
import org.kiki_cpg_v2.service.SmsService;
import org.kiki_cpg_v2.util.AppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CronServiceImpl implements CronService {
	final static Logger logger = LoggerFactory.getLogger(CronServiceImpl.class);

	@Autowired
	private CronErrorRepository cronErrorRepository;

	@Autowired
	private SmsService smsService;

	@Autowired
	private IdeabizRepository ideabizRepository;

	@Autowired
	private CronReportRepository cronReportRepository;

	@Autowired
	private IdeabizService ideabizService;

	@Autowired
	private MobitelService mobitelService;

	@Autowired
	private PaymentMethodService paymentMethodService;

	@Autowired
	private CronMetaDataRepository cronMetaDataRepository;

	@Autowired
	private ViewerSubscriptionRepository viewerSubscriptionRepository;

	@Override
	public void startDialogCron(String cronName, String ipAddress, String date, String time) {

		logger.info("dialog cron started");

		try {
			if (cronMetaDataRepository.findOneByCronNameAndStatusAndCronStatus(AppConstant.DIALOG, AppConstant.ACTIVE,
					AppConstant.ACTIVE) == null) {
				smsService.sendSms(AppConstant.CRON_NOTIFY_MOBILES, "Dialog Cron not started.");
				System.out.println("Dialog Cron  not started");
				return;
			} else {
				System.out.println("Dialog Cron started");
			}

			smsService.sendSms(AppConstant.CRON_NOTIFY_MOBILES,
					"Ideabiz Cron " + cronName + " Strated " + date + " " + time);
			List<IdeabizViewerCusrtomEntity> ideabizViewerJoinEntities = ideabizRepository
					.getIdeabizViewerCusrtomEntityExpireBeforeToday();

			if (ideabizViewerJoinEntities != null) {

				ideabizViewerJoinEntities.forEach(e -> {
					System.out.println("Dialog Viewer Id : " + e.getViewerId());
					logger.info("Dialog Viewer Id : " + e.getViewerId());
				});

				CronReportEntity cronReportEntity = saveCron(cronName, ipAddress, date, time, "Dialog");
				if (cronReportEntity != null) {
					smsService.sendSms(AppConstant.CRON_NOTIFY_MOBILES,
							"Ideabiz Pending Subscribtion Count Is : " + ideabizViewerJoinEntities.size()
									+ " - Cron Started :" + time + " -Server Ip : " + ipAddress);

					Integer transactionCount = 0;

					for (IdeabizViewerCusrtomEntity ideabizViewerCusrtomEntity : ideabizViewerJoinEntities) {
						Double amount = paymentMethodService.getPaymentPlanAmount(ideabizViewerCusrtomEntity.getDays(),
								4);
						try {
							String resp = ideabizService.processIdeabizPayment(null,
									ideabizViewerCusrtomEntity.getViewerId(), ideabizViewerCusrtomEntity.getDays(),
									ideabizViewerCusrtomEntity.getMobile(), amount, false, true,
									cronReportEntity.getCronId());
							if (resp.equalsIgnoreCase("Success")) {
								transactionCount += 1;
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.error(e.getLocalizedMessage());

							cronErrorSave(cronReportEntity.getCronId(), e.getMessage(), e.getLocalizedMessage(),
									"IS crtInv", ideabizViewerCusrtomEntity.getViewerId());
						}

					}

					String endTime = new SimpleDateFormat("HH:mm:ss").format(new Date());

					smsService.sendSms(AppConstant.CRON_NOTIFY_MOBILES,
							"Ideabiz Done Subscribtion Count Is :" + transactionCount + " / "
									+ ideabizViewerJoinEntities.size() + " -Cron Stoped :" + endTime + " -Server Ip : "
									+ ipAddress);

					cronReportEntity.setEndTime(endTime);
					cronReportEntity = saveCron(cronReportEntity);

				}
			}

			System.out.println("Mobitel cron complete");
			logger.info("Dialog cron complete");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Dialog CRON method completed");
			logger.info("Dialog CRON method completed");
		}

	}

	@Override
	public void startMobitelCron(String cronName, String ipAddress, String date, String time) {
		logger.info("mobitel cron started");
		try {
			if (cronMetaDataRepository.findOneByCronNameAndStatusAndCronStatus(AppConstant.DIALOG, AppConstant.ACTIVE,
					AppConstant.ACTIVE) == null) {
				smsService.sendSms(AppConstant.CRON_NOTIFY_MOBILES, "Mobitel Cron not started.");
				System.out.println("mobitel Cron not started");
				return;
			} else {
				System.out.println("mobitel Cron started");
			}

			smsService.sendSms(AppConstant.CRON_NOTIFY_MOBILES,
					"Mobitel Cron " + cronName + " Strated " + date + " " + time);

			List<ViewerSubscriptionCustomEntity> viewerSubscriptionCustomEntities = viewerSubscriptionRepository
					.getViewerSubscriptionCustomEntityExpireBeforeToday();

			viewerSubscriptionCustomEntities.forEach(e -> {
				System.out.println("Mobitel Viewer Id : " + e.getViewerId());
				logger.info("Mobitel Viewer Id : " + e.getViewerId());
			});

			if (viewerSubscriptionCustomEntities != null) {
				CronReportEntity cronReportEntity = saveCron(cronName, ipAddress, date, time, "Mobitel");
				if (cronReportEntity != null) {
					smsService.sendSms(AppConstant.CRON_NOTIFY_MOBILES,
							"Mobitel Pending Subscribtion Count Is : " + viewerSubscriptionCustomEntities.size()
									+ " - Cron Started :" + time + " -Server Ip : " + ipAddress);
					Integer transactionCount = 0;
					for (ViewerSubscriptionCustomEntity viewerSubscriptionCustomEntity : viewerSubscriptionCustomEntities) {
						try {
							String resp = mobitelService.cronPay(viewerSubscriptionCustomEntity.getMobile(),
									viewerSubscriptionCustomEntity.getViewerId(), "1",
									viewerSubscriptionCustomEntity.getDays(), true, cronReportEntity.getCronId());
							if (resp.equalsIgnoreCase("success")) {
								transactionCount += 1;
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.error(e.getLocalizedMessage());
							cronErrorSave(cronReportEntity.getCronId(), e.getMessage(), e.getLocalizedMessage(),
									"IS crtInv", viewerSubscriptionCustomEntity.getViewerId());
						}

					}

					String endTime = new SimpleDateFormat("HH:mm:ss").format(new Date());

					smsService.sendSms(AppConstant.CRON_NOTIFY_MOBILES,
							"Mobitel Done Subscribtion Count Is :" + transactionCount + " / "
									+ viewerSubscriptionCustomEntities.size() + " -Cron Stoped :" + endTime
									+ " -Server Ip : " + ipAddress);

					cronReportEntity.setEndTime(endTime);
					cronReportEntity = saveCron(cronReportEntity);
				}
			}
			System.out.println("Mobitel cron complete");
			logger.info("Mobitel cron complete");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Mobitel CRON method completed");
			logger.info("Mobitel CRON method completed");
		}

	}

	@Override
	public CronReportEntity saveCron(String cronName, String ipAddress, String date, String time, String type) {
		CronReportEntity entity = new CronReportEntity();
		entity.setCronType(type);
		entity.setCronName(cronName);
		entity.setServerIp(ipAddress);
		entity.setStartedDate(date);
		entity.setStartTime(time);

		entity = cronReportRepository.save(entity);
		if (entity.getCronId() > 0) {
			return entity;
		}
		return null;
	}

	@Override
	public CronReportEntity saveCron(CronReportEntity cronReportEntity) {
		cronReportEntity = cronReportRepository.save(cronReportEntity);
		if (cronReportEntity.getCronId() > 0) {
			return cronReportEntity;
		}
		return null;

	}

	@Override
	public void cronErrorSave(Integer cronId, String errorDesc, String errorMsg, String systemPage, Integer viewerId) {
		try {
			CronErrorEntity entity = new CronErrorEntity();
			entity.setCronId(cronId);
			entity.setErrorDate(new Date());
			entity.setErrorDesc(errorDesc);
			entity.setErrorMsg(errorMsg);
			entity.setSystemPage(systemPage);
			entity.setViewerId(viewerId);

			cronErrorRepository.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
