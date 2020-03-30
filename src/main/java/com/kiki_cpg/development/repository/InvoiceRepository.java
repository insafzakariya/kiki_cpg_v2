package com.kiki_cpg.development.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.kiki_cpg.development.entity.Invoice;
import com.kiki_cpg.development.repository.Custom.InvoiceRepositoryCustom;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice,Integer> , InvoiceRepositoryCustom {

}
