package org.kiki_cpg_v2;

import org.kiki_cpg_v2.scheduler.CronScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KikiCpgV2Application {
	
	@Autowired
	private CronScheduler cronScheduler;

	public static void main(String[] args) {
		SpringApplication.run(KikiCpgV2Application.class, args);
	}

}
