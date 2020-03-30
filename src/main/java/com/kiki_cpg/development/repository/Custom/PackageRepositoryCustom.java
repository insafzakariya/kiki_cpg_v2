package com.kiki_cpg.development.repository.Custom;


import java.util.List;

import com.kiki_cpg.development.entity.Packages;

public interface PackageRepositoryCustom {
    List<Packages> getPackageById(int newPackageID);
}
