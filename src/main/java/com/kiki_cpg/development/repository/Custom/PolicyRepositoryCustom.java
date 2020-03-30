package com.kiki_cpg.development.repository.Custom;


import org.springframework.stereotype.Repository;

import com.kiki_cpg.development.entity.Policies;

import java.util.List;

@Repository
public interface PolicyRepositoryCustom {
    List<Policies> getPoliciesByPackageID(int newPackageID);
}
