package com.kiki_cpg.development.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kiki_cpg.development.entity.Config;
import com.kiki_cpg.development.entity.ViewerUnsubcription;

@Repository
public interface ViewerUnsubcriptionRepository extends JpaRepository<ViewerUnsubcription,Integer> {

}
