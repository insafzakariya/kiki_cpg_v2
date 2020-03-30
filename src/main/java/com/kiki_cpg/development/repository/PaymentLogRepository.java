package com.kiki_cpg.development.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.kiki_cpg.development.entity.PaymentLog;

public interface PaymentLogRepository extends JpaRepository<PaymentLog,Integer> {
}
