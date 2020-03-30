package com.kiki_cpg.development.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiki_cpg.development.entity.CronConfig;

public interface CronConfigRepository extends JpaRepository<CronConfig,Integer> {
}
