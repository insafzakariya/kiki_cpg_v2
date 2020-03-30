package com.kiki_cpg.development.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kiki_cpg.development.entity.CronError;

@Repository
public interface CronErrosRepository extends JpaRepository<CronError,Integer>{

}
