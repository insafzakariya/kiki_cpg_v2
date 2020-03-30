package com.kiki_cpg.development.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiki_cpg.development.entity.ViewerPackages;
import com.kiki_cpg.development.repository.Custom.ViewerPackagesRepositoryCustom;

public interface ViewerPackagesRepository extends JpaRepository<ViewerPackages,Integer>, ViewerPackagesRepositoryCustom {
}
