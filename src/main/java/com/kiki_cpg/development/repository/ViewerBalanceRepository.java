package com.kiki_cpg.development.repository;

import org.springframework.stereotype.Repository;

import com.kiki_cpg.development.entity.ViewerBalance;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface ViewerBalanceRepository extends JpaRepository<ViewerBalance,Integer>{

}
