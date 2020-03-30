package com.kiki_cpg.development.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kiki_cpg.development.entity.Packages;
import com.kiki_cpg.development.repository.Custom.PackageRepositoryCustom;

import java.util.Date;
import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<Packages, Integer>, PackageRepositoryCustom {
    Packages findByPackageId(int pacId);


    @Query(value = "SELECT * FROM packages where PackageID=:packageId and ActivityStartDate <=:todayDate  and ActivityEndDate >=:todayDate", nativeQuery = true)
    List<Object[]>getPackagesByIs(@Param("packageId")int packageId, @Param("todayDate") Date todayDate);
}
