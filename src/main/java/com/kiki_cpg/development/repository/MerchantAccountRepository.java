package com.kiki_cpg.development.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kiki_cpg.development.entity.MerchantAccount;
import com.kiki_cpg.development.repository.Custom.MerchantAccountRepositoryCustom;

import java.util.Date;
import java.util.List;

public interface MerchantAccountRepository extends JpaRepository<MerchantAccount,Integer>, MerchantAccountRepositoryCustom {
    @Query(value = "select * from merchant_account m where m.IsSuccess=true and m.Date<=:today and m.ViewerId=:viewerID", nativeQuery = true)
    public List<Object[]> getSubcribeViwersInMobitel(@Param("viewerID") Integer viewerID, @Param("today") Date today);

	
    
  
}
