package com.kiki_cpg.development.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kiki_cpg.development.entity.CronReport;

import java.text.SimpleDateFormat;
import java.util.List;

@Repository
public interface CronRepository extends JpaRepository<CronReport,Integer> {
    List<CronReport> findByStartedDate(String date);
}
