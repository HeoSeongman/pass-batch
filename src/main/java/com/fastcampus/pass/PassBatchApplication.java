package com.fastcampus.pass;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
@SpringBootApplication
@EnableBatchProcessing
public class PassBatchApplication {



	public static void main(String[] args) {
		System.out.println("애플리케이션 시작");
		SpringApplication.run(PassBatchApplication.class, args);
		System.out.println("애플리케이션 끝");
	}


//	@RequiredArgsConstructor
//	@Component
//	class CommandLineStarter implements CommandLineRunner {
//
//		private final ApplicationContext context;
//		private final JobLauncher jobLauncher;
//		private final Job job;
//
//
//		@Value("${job.name}")
//		private String jobName;
//
//
//		@Override
//		public void run(String... args) throws Exception {
//			Job job = context.getBean(jobName, Job.class);
//			System.out.println("잡 이름 : " + job.getName());
////			//TODO 커맨드 라인에서 파라미터를 받아 job 파라미터로 넘겨주도록 설정함 => https://louisdev.tistory.com/25, 이 방법도 있는거 같다 => https://howtodoinjava.com/spring-batch/java-config-multiple-steps/
//			jobLauncher.run(job, new JobParameters());
//			JobParameters jobParameters = new JobParametersBuilder()
//					.addString("name", "myJob")
//					.toJobParameters();
//			System.out.println(job.getName());
//			jobLauncher.run(job, jobParameters);
//		}
//	}


}
