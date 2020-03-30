package com.kiki_cpg.development.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.kiki_cpg.development.entity.Config;

public interface ConfigRepository extends JpaRepository<Config,Integer> {
}
