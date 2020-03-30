package com.kiki_cpg.development.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiki_cpg.development.entity.Policies;
import com.kiki_cpg.development.entity.ViewerPackages;
import com.kiki_cpg.development.entity.ViewerPolicies;
import com.kiki_cpg.development.repository.ViewerPoliciesRepository;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Transactional
@Service
public class ViewerPoliciesService {
    @Autowired
    ViewerPoliciesRepository viewerPoliciesRepository;

    public boolean addViewerPolicies(List<Policies> policies, int viewerID, int nofDaysForPolicy, ViewerPackages viewerPackages) {
        boolean actionStatus = false;

        if (!policies.isEmpty()) {
            Iterator listIterator = policies.iterator();
            while (listIterator.hasNext()) {
                Policies policy = (Policies) listIterator.next();
                ViewerPolicies viewerPolicy = new ViewerPolicies();

                viewerPolicy.setViewerId(viewerID);
//                Policies policy = new Policies();
                policy.setPolicyId(policy.getPolicyId());
                viewerPolicy.setPolicy(policy);
                viewerPolicy.setViewerPackage(viewerPackages);
                // viewerPolicy.setStartDate(packagePolicy.getPolicy().getValidFrom());
                // viewerPolicy.setEndDate(packagePolicy.getPolicy().getValidTo());
                // New CR Policy date period => Today to today + nof days

                Date currentDate = new Date();
                Calendar c = Calendar.getInstance();

                // Start
                c.setTime(currentDate);
                c.add(Calendar.DATE, -1);
                viewerPolicy.setStartDate(c.getTime());

                // End
                c.setTime(currentDate);
                c.add(Calendar.DATE, nofDaysForPolicy);
                viewerPolicy.setEndDate(c.getTime());
                viewerPolicy.setLastUpdated(new Date());

                viewerPolicy.setStatus(1);
                viewerPoliciesRepository.save(viewerPolicy);
                actionStatus = true;

            }
        }
        return actionStatus;
    }
}
