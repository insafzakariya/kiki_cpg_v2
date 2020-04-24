package com.kiki_cpg.development.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiki_cpg.development.entity.PaymentMethods;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethods, Integer>{

}
