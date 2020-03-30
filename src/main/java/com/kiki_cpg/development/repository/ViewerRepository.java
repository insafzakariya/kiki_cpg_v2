package com.kiki_cpg.development.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kiki_cpg.development.entity.Viewers;
import com.kiki_cpg.development.repository.Custom.ViewerRepositoryCustom;

import java.util.List;

@Repository
public interface ViewerRepository extends JpaRepository<Viewers, Integer>, ViewerRepositoryCustom {
    Viewers findByViewerId(Integer viewerID);

}
