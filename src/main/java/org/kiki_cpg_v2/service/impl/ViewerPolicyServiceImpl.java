package org.kiki_cpg_v2.service.impl;

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
	

	@Override
	@Transactional
	public String updateViewerPolicy(ViewerPolicyUpdateRequestDto viewerPolicyUpdateRequestDto) {

		ViewerPackageEntity viewerPackageEntity = viewerPackageRepository
				.findFirstByViewerIdAndStatus(viewerPolicyUpdateRequestDto.getViewerId(), AppConstant.ACTIVE);

		if (viewerPackageEntity != null) {
			
			PackageEntity packageEntity = packageRepository.findById(viewerPackageEntity.getPackageId()).get();
			
			if(packageEntity != null) {
				if (packageEntity.getId() == viewerPolicyUpdateRequestDto.getPackageId()) {

					List<PackagePolicyEntity> packagePolicyEntities = new ArrayList<>();
					for (PackagePolicyEntity packagePolicyEntity : packageEntity
							.getPackagePoliciesEntities()) {
						if (packagePolicyEntity.getPolicyEntity().getValidFrom().getTime() <= new Date().getTime()
								&& packagePolicyEntity.getPolicyEntity().getValidTo().getTime() >= new Date().getTime()) {
							packagePolicyEntities.add(packagePolicyEntity);
						}
					}
					// This codes useful for update policies with adding remaining dates

					/*
					 * List<ViewerPolicyEntity> viewerPolicyEntities =
					 * viewerPackageEntity.getViewerPolicyEntities(); List<ViewerPolicyEntity>
					 * activeViewerPolicyEntities = new ArrayList<ViewerPolicyEntity>();
					 * 
					 * for (ViewerPolicyEntity viewerPolicyEntity : viewerPolicyEntities) {
					 * if(viewerPolicyEntity.getEndDate().getTime() > new Date().getTime()) {
					 * if(!activeViewerPolicyEntities.isEmpty()) { boolean isAvailable = false; for
					 * (ViewerPolicyEntity activeViewerPolicyEntity : activeViewerPolicyEntities) {
					 * 
					 * if(activeViewerPolicyEntity.getPolicyEntity().getId() ==
					 * viewerPolicyEntity.getPolicyEntity().getId()) { isAvailable = true; } }
					 * if(!isAvailable) { activeViewerPolicyEntities.add(viewerPolicyEntity); } }
					 * else { activeViewerPolicyEntities.add(viewerPolicyEntity); } } }
					 */

					List<ViewerPolicyEntity> viewerPolicyEntities = new ArrayList<>();
					packagePolicyEntities.forEach(e -> {
						viewerPolicyEntities.add(
								getViewerPolicyEntity(packageEntity, e, viewerPackageEntity, viewerPolicyUpdateRequestDto.getViewerId()));
					});
					
					if(viewerPolicyEntities != null && !viewerPolicyEntities.isEmpty()) {
						if(viewerPolicyRepository.saveAll(viewerPolicyEntities) != null) {
							return "success";
						} else {
							return "Viewer Policies save fail.";
						}
					} else {
						return "No policies for ViewerPackageEntity id : " + viewerPackageEntity.getId() ;
					}

				} else {
					List<ViewerPackageEntity> viewerPackageEntities = viewerPackageRepository
							.findByViewerIdAndStatus(viewerPolicyUpdateRequestDto.getViewerId(), AppConstant.ACTIVE);
					viewerPackageEntities.forEach(e-> {
						e.setStatus(AppConstant.INACTIVE);
						e.setModifiedOn(new Date());
					});
					
					if(viewerPackageRepository.saveAll(viewerPackageEntities) != null) {
						PackageEntity packageEntityNew = packageRepository.findById(viewerPolicyUpdateRequestDto.getPackageId()).get();
						ViewerPackageEntity tempViewerPackageEntity = getViewerPackageEntity(packageEntityNew , viewerPolicyUpdateRequestDto.getViewerId());
						ViewerPackageEntity newViewerPackageEntity = viewerPackageRepository.save(tempViewerPackageEntity);
						List<ViewerPolicyEntity> viewerPolicyEntities = new ArrayList<>();
						packageEntityNew.getPackagePoliciesEntities().forEach(e ->{
							viewerPolicyEntities.add(getViewerPolicyEntity(packageEntityNew, e, newViewerPackageEntity, viewerPolicyUpdateRequestDto.getViewerId()));
						});
						
						if(viewerPolicyEntities != null && !viewerPolicyEntities.isEmpty()) {
							if(viewerPolicyRepository.saveAll(viewerPolicyEntities) != null) {
								return "success";
							} else {
								return "Viewer Policies save fail.";
							}
						} else {
							return "No policies for package id : " + packageEntityNew.getId() ;
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

	public ViewerPolicyEntity getViewerPolicyEntity(PackageEntity packageEntity, PackagePolicyEntity packagePolicyEntity, ViewerPackageEntity viewerPackageEntity,
			Integer viewerId) {
		ViewerPolicyEntity entity = new ViewerPolicyEntity();

		entity.setPolicyEntity(packagePolicyEntity.getPolicyEntity());
		entity.setStartDate(new Date());
		entity.setStatus(AppConstant.ACTIVE);
		entity.setViewerId(viewerId);
		entity.setViewerPackageEntity(viewerPackageEntity);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, packageEntity.getAvailableDays());
		entity.setEndDate(c.getTime());
		return entity;
	}

	@Override
	public ViewerPolicyUpdateRequestDto getViewerPolicyUpdateRequestDto(Integer viewerId, Integer packageId) {
		ViewerPolicyUpdateRequestDto dto = new ViewerPolicyUpdateRequestDto();
		
		dto.setPackageId(packageId);
		dto.setViewerId(viewerId);
		
		return dto;
	}

}
