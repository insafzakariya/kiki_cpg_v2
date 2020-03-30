package com.kiki_cpg.development.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kiki_cpg.development.entity.CronViewerReport;
import com.kiki_cpg.development.repository.Custom.CronViewerReportRepositoryCustom;

import java.util.Date;
import java.util.List;


public interface CronViewerReportRepository extends JpaRepository<CronViewerReport,Integer>, CronViewerReportRepositoryCustom {

}
