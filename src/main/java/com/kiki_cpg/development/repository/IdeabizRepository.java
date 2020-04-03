package com.kiki_cpg.development.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiki_cpg.development.entity.Ideabiz;
import com.kiki_cpg.development.repository.Custom.IdeabizRepositoryCustom;

import java.util.Date;
import java.util.List;

public interface IdeabizRepository extends JpaRepository<Ideabiz, Integer>, IdeabizRepositoryCustom {

	// List<Ideabiz> findBySubscribe(Integer subscribe);

	// List<Ideabiz>findBySubscribeAndPolicy_expire_atEqualsOrPolicy_expire_atBefore(Integer
	// subscribe,Date expire_at,Date expire_att);

	List<Ideabiz> findBySubscribe(Integer subscribe);

	Ideabiz findOneByViwerIdAndSubscribe(Integer viewerID, int i);

}
