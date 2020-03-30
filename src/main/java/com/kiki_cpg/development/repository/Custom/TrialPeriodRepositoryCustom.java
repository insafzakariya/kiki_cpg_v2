package com.kiki_cpg.development.repository.Custom;

import com.kiki_cpg.development.entity.ViewerTrialPeriodAssociation;

public interface TrialPeriodRepositoryCustom {
    ViewerTrialPeriodAssociation getOnGoingViewerTrialPeriodAssociation(Integer viewerId);

    boolean updateViewerTrialPeriodAssociationOnGoingStatus(Integer viewerId, boolean b);
}
