package com.kiki_cpg.development.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kiki_cpg.development.entity.Config;
import com.kiki_cpg.development.entity.SubscriptionPayments;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SubscriptionPaymentsRepository extends JpaRepository<SubscriptionPayments,Integer> {

}
