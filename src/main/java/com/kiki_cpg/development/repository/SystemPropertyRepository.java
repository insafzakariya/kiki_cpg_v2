package com.kiki_cpg.development.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiki_cpg.development.entity.SystemProperty;

public interface SystemPropertyRepository extends JpaRepository<SystemProperty, Integer>{

	SystemProperty findOneByKeyValue(String string);

}
