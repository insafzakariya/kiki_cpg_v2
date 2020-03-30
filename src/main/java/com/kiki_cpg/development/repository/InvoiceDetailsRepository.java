package com.kiki_cpg.development.repository;

import com.kiki_cpg.development.entity.InvoiceDetails;
import com.kiki_cpg.development.repository.Custom.InvoiceDetailsRepositoryCustom;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceDetailsRepository extends JpaRepository<InvoiceDetails,Integer> , InvoiceDetailsRepositoryCustom {

}
