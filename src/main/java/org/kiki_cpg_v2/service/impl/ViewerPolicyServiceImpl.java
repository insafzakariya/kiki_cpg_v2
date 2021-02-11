package org.kiki_cpg_v2.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.kiki_cpg_v2.dto.request.ViewerPolicyUpdateRequestDto;
import org.kiki_cpg_v2.entity.PackageEntity;
import org.kiki_cpg_v2.entity.PackagePolicyEntity;
import org.kiki_cpg_v2.entity.ViewerPackageEntity;
import org.kiki_cpg_v2.entity.ViewerPolicyEntity;
import org.kiki_cpg_v2.repository.PackageRepository;
import org.kiki_cpg_v2.repository.ViewerPackageRepository;
import org.kiki_cpg_v2.repository.ViewerPolicyRepository;
import org.kiki_cpg_v2.service.ViewerPolicyService;
import org.kiki_cpg_v2.util.AppConstant;
import org.kiki_cpg_v2.util.AppUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ViewerPolicyServiceImpl implements ViewerPolicyService {

	@Autowired
	private ViewerPackageRepository viewerPackageRepository;

	@Autowired
	private ViewerPolicyRepository viewerPolicyRepository;

	@Autowired
	private PackageRepository packageRepository;
	
	@Autowired
	private AppUtility appUtility;

	@Override
	@Transactional
	public String updateViewerPolicy(ViewerPolicyUpdateRequestDto viewerPolicyUpdateRequestDto, Integer dateCount) {

		ViewerPackageEntity viewerPackageEntity = viewerPackageRepository
				.findFirstByViewerIdAndStatus(viewerPolicyUpdateRequestDto.getViewerId(), AppConstant.ACTIVE);

		if (viewerPackageEntity != null) {

			PackageEntity packageEntity = packageRepository.findById(viewerPackageEntity.getPackageId()).get();

			if (packageEntity != null) {
				if (packageEntity.getId() == viewerPolicyUpdateRequestDto.getPackageId()) {

					List<PackagePolicyEntity> packagePolicyEntities = new ArrayList<>();
					for (PackagePolicyEntity packagePolicyEntity : packageEntity.getPackagePoliciesEntities()) {
						if (packagePolicyEntity.getPolicyEntity().getValidFrom().getTime() <= new Date().getTime()
								&& packagePolicyEntity.getPolicyEntity().getValidTo().getTime() >= new Date()
										.getTime()) {
							packagePolicyEntities.add(packagePolicyEntity);
						}
					}

					List<ViewerPolicyEntity> viewerPolicyEntities = new ArrayList<>();
					packagePolicyEntities.forEach(e -> {
						try {
							viewerPolicyEntities.add(getViewerPolicyEntity(packageEntity, e, viewerPackageEntity,
									viewerPolicyUpdateRequestDto.getViewerId(), dateCount));
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					});

					if (viewerPolicyEntities != null && !viewerPolicyEntities.isEmpty()) {
						if (viewerPolicyRepository.saveAll(viewerPolicyEntities) != null) {
							return "success";
						} else {
							return "Viewer Policies save fail.";
						}
					} else {
						return "No policies for ViewerPackageEntity id : " + viewerPackageEntity.getId();
					}

				} else {
					List<ViewerPackageEntity> viewerPackageEntities = viewerPackageRepository
							.findByViewerIdAndStatus(viewerPolicyUpdateRequestDto.getViewerId(), AppConstant.ACTIVE);
					viewerPackageEntities.forEach(e -> {
						e.setStatus(AppConstant.INACTIVE);
						e.setModifiedOn(new Date());
					});

					if (viewerPackageRepository.saveAll(viewerPackageEntities) != null) {
						System.out.println("viewerPolicyUpdateRequestDto.getPackageId() :" + viewerPolicyUpdateRequestDto.getPackageId());
						PackageEntity packageEntityNew = packageRepository
								.findById(viewerPolicyUpdateRequestDto.getPackageId()).get();
						System.out.println(packageEntityNew.getName());
						ViewerPackageEntity tempViewerPackageEntity = getViewerPackageEntity(packageEntityNew,
								viewerPolicyUpdateRequestDto.getViewerId());
						ViewerPackageEntity newViewerPackageEntity = viewerPackageRepository
								.save(tempViewerPackageEntity);
						List<ViewerPolicyEntity> viewerPolicyEntities = new ArrayList<>();
						packageEntityNew.getPackagePoliciesEntities().forEach(e -> {
							try {
								viewerPolicyEntities.add(getViewerPolicyEntity(packageEntityNew, e, newViewerPackageEntity,
										viewerPolicyUpdateRequestDto.getViewerId(),dateCount));
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						});

						if (viewerPolicyEntities != null && !viewerPolicyEntities.isEmpty()) {
							if (viewerPolicyRepository.saveAll(viewerPolicyEntities) != null) {
								return "success";
							} else {
								return "Viewer Policies save fail.";
							}
						} else {
							return "No policies for package id : " + packageEntityNew.getId();
						}

					} else {
						return "Can't update Exist viewer policies";
					}
				}
			} else {
				return "Package not found for Id : " + viewerPolicyUpdateRequestDto.getViewerId();
			}

		} else {
			return "Package not found for Id : " + viewerPolicyUpdateRequestDto.getViewerId();
		}

	}

	public ViewerPackageEntity getViewerPackageEntity(PackageEntity packageEntity, Integer viewerId) {
		ViewerPackageEntity entity = new ViewerPackageEntity();
		entity.setPackageId(packageEntity.getId());
		entity.setStatus(AppConstant.ACTIVE);
		entity.setViewerId(viewerId);
		return entity;
	}

	public ViewerPolicyEntity getViewerPolicyEntity(PackageEntity packageEntity,
			PackagePolicyEntity packagePolicyEntity, ViewerPackageEntity viewerPackageEntity, Integer viewerId, Integer dateCount) throws ParseException {
		ViewerPolicyEntity entity = new ViewerPolicyEntity();

		entity.setPolicyEntity(packagePolicyEntity.getPolicyEntity());
		entity.setStartDate(new Date());
		entity.setStatus(AppConstant.ACTIVE);
		entity.setViewerId(viewerId);
		entity.setViewerPackageEntity(viewerPackageEntity);
		System.out.println(dateCount);
		if(dateCount > 0) {
			System.out.println(1);
			entity.setEndDate(appUtility.getbeforeDay(dateCount, appUtility.getLastMinitue()));
		} else {
			System.out.println(2);
			System.out.println( packageEntity.getAvailableDays());
			entity.setEndDate(appUtility.getbeforeDay( packageEntity.getAvailableDays(), appUtility.getLastMinitue()));
		}
		
		entity.setLastUpdated(new Date());
		return entity;
	}

	@Override
	public ViewerPolicyUpdateRequestDto getViewerPolicyUpdateRequestDto(Integer viewerId, Integer packageId) {
		ViewerPolicyUpdateRequestDto dto = new ViewerPolicyUpdateRequestDto();

		dto.setPackageId(packageId);
		dto.setViewerId(viewerId);

		return dto;
	}

	@Override
	public String deactivatePolicy(Integer viewerId, Integer newViewerId, boolean isTranfser, Integer packageId) {
		Date curDate = new Date();
		List<ViewerPolicyEntity> viewerPolicyEntities = viewerPolicyRepository
				.findByViewerIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndStatus(viewerId, curDate, curDate, AppConstant.ACTIVE);
		
		PackageEntity packageEntity = packageRepository.findById(packageId).get();
		ViewerPackageEntity viewerPackageEntity = viewerPackageRepository.findFirstByViewerIdAndStatus(newViewerId, AppConstant.ACTIVE);
		System.out.println("viewerPackageEntity : " + viewerPackageEntity.getId());
		List< Integer > policyIds = new ArrayList<Integer>();
		if(packageEntity != null) {
			packageEntity.getPackagePoliciesEntities().forEach(e-> {
				policyIds.add(e.getPolicyEntity().getId());
			});
		}
		
		
		
		List<ViewerPolicyEntity> newViewerPolicyEntities = null;
		if (isTranfser) {
			newViewerPolicyEntities = new ArrayList<ViewerPolicyEntity>();
		}

		if (viewerPolicyEntities != null && !viewerPolicyEntities.isEmpty()) {
			for (ViewerPolicyEntity viewerPolicyEntity : viewerPolicyEntities) {
				if(policyIds.contains(viewerPolicyEntity.getPolicyEntity().getId())) {
					if (newViewerPolicyEntities != null) {
						newViewerPolicyEntities.add(getCopyViewerPolicyEntity(viewerPolicyEntity, newViewerId, viewerPackageEntity));
					}
					viewerPolicyEntity.setStatus(AppConstant.INACTIVE);
				}
			}
		}

		viewerPolicyRepository.saveAll(viewerPolicyEntities);
		if(isTranfser) {
			viewerPolicyRepository.saveAll(newViewerPolicyEntities);
		}

		return "success";
	}

	@Override
	public ViewerPolicyEntity getCopyViewerPolicyEntity(ViewerPolicyEntity viewerPolicyEntity, Integer viewerId, ViewerPackageEntity viewerPackageEntity) {
		ViewerPolicyEntity entity = new ViewerPolicyEntity();
		entity.setEndDate(viewerPolicyEntity.getEndDate());
		entity.setLastUpdated(viewerPolicyEntity.getLastUpdated());
		entity.setPolicyEntity(viewerPolicyEntity.getPolicyEntity());
		entity.setStartDate(viewerPolicyEntity.getStartDate());
		entity.setStatus(viewerPolicyEntity.getStatus());
		entity.setViewerId(viewerId);
		entity.setViewerPackageEntity(viewerPackageEntity);
		return entity;
	}

	@Override
	public boolean checkStatus(Integer viewerId, Integer packageId) {
		Date curDate = new Date();
		List<ViewerPolicyEntity> viewerPolicyEntities = viewerPolicyRepository.findByViewerIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(viewerId, curDate, curDate);
		
		PackageEntity packageEntity = packageRepository.findById(packageId).get();
		List< Integer > policyIds = new ArrayList<Integer>();
		if(packageEntity != null) {
			packageEntity.getPackagePoliciesEntities().forEach(e-> {
				policyIds.add(e.getPolicyEntity().getId());
			});
		}
		
		if(viewerPolicyEntities != null && !viewerPolicyEntities.isEmpty()) {
			for (ViewerPolicyEntity viewerPolicyEntity : viewerPolicyEntities) {
				if(policyIds.contains(viewerPolicyEntity.getPolicyEntity().getId()) && viewerPolicyEntity.getStatus() == AppConstant.ACTIVE) {
					return true;
				}
			}
			
			
		}
		
		
		
		return false;
	}

	@Override
	public boolean findViewerPoliceExist(Integer viewerId, Integer packageId) {
		Date curDate = new Date();
		List<ViewerPolicyEntity> viewerPolicyEntities = viewerPolicyRepository
				.findByViewerIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndStatus(viewerId, curDate, curDate, AppConstant.ACTIVE);
		
		PackageEntity packageEntity = packageRepository.findById(packageId).get();
		ViewerPackageEntity viewerPackageEntity = viewerPackageRepository.findFirstByViewerIdAndStatus(viewerId, AppConstant.ACTIVE);
		System.out.println("viewerPackageEntity : " + viewerPackageEntity.getId());
		List< Integer > policyIds = new ArrayList<Integer>();
		if(packageEntity != null) {
			packageEntity.getPackagePoliciesEntities().forEach(e-> {
				policyIds.add(e.getPolicyEntity().getId());
			});
		}
		
		if (viewerPolicyEntities != null && !viewerPolicyEntities.isEmpty()) {
			for (ViewerPolicyEntity viewerPolicyEntity : viewerPolicyEntities) {
				if(policyIds.contains(viewerPolicyEntity.getPolicyEntity().getId())) {
					return true;
				}
			}
		}

		return false;
	}

}
