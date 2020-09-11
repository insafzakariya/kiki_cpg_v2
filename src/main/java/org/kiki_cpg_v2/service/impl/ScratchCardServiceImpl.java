package org.kiki_cpg_v2.service.impl;

import java.util.Date;

import org.kiki_cpg_v2.dto.ScratchCardPaymentDto;
import org.kiki_cpg_v2.dto.request.ViewerPolicyUpdateRequestDto;
import org.kiki_cpg_v2.entity.TblScratchCardCodeEntity;
import org.kiki_cpg_v2.repository.TblScratchCardCodeRepository;
import org.kiki_cpg_v2.service.ScratchCardService;
import org.kiki_cpg_v2.service.ViewerCodeService;
import org.kiki_cpg_v2.service.ViewerPolicyService;
import org.kiki_cpg_v2.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScratchCardServiceImpl implements ScratchCardService {

	@Autowired
	private TblScratchCardCodeRepository tblScratchCardCodeRepository;

	@Autowired
	private ViewerCodeService viewerCodeService;

	@Autowired
	private ViewerPolicyService viewerPolicyService;

	@Override
	public String setPayment(ScratchCardPaymentDto scratchCardPaymentDto) throws Exception {
		TblScratchCardCodeEntity scratchCardCodeEntity = getValiedCardCodeEntity(scratchCardPaymentDto.getCode());

		String resp = "error";

		if (scratchCardCodeEntity != null) {
			if (scratchCardCodeEntity.getTblScratchCardEntity().getCardType() == 2) {
				scratchCardCodeEntity.setCardStatus(2);
				tblScratchCardCodeRepository.save(scratchCardCodeEntity);
			}

			if (!viewerCodeService.isAlreadyUsedViewerScratchCardCode(scratchCardPaymentDto.getViewerId(),
					scratchCardCodeEntity.getId())
					|| scratchCardCodeEntity.getTblScratchCardEntity().getCardType() == 2) {
				if (viewerCodeService.addViewerCode(scratchCardPaymentDto.getViewerId(),
						scratchCardCodeEntity.getTblScratchCardEntity().getId())) {
					ViewerPolicyUpdateRequestDto policyUpdateRequestDto = new ViewerPolicyUpdateRequestDto();
					policyUpdateRequestDto.setViewerId(scratchCardPaymentDto.getViewerId());
					policyUpdateRequestDto.setPackageId(scratchCardCodeEntity.getTblScratchCardEntity().getPackageID());

					resp = viewerPolicyService.updateViewerPolicy(policyUpdateRequestDto, -1);

				} else {
					resp = "Could not update the package";
				}
			} else {
				resp = "Already used code";
			}
		} else {
			resp = "Invalid card code";
		}

		return resp;
	}

	@Override
	public TblScratchCardCodeEntity getValiedCardCodeEntity(String code) throws Exception {
		TblScratchCardCodeEntity scratchCardCodeEntity = tblScratchCardCodeRepository
				.findOneByCardCodeAndCardStatus(code, AppConstant.ACTIVE);
		Date curDate = new Date();
		if (scratchCardCodeEntity != null && scratchCardCodeEntity.getTblScratchCardEntity() != null) {
			if (scratchCardCodeEntity.getTblScratchCardEntity().getStatus() == 1
					&& scratchCardCodeEntity.getTblScratchCardEntity().getActivityStartDate().getTime() <= curDate
							.getTime()
					&& scratchCardCodeEntity.getTblScratchCardEntity().getActivityEndDate().getTime() >= curDate
							.getTime()) {
				return scratchCardCodeEntity;
			}
		}
		return null;
	}

}
