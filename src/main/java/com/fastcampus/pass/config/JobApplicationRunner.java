package com.fastcampus.pass.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobApplicationRunner implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final Job job;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", job.getName())
                .toJobParameters();
        System.out.println("JobApplicationRunner 잡 런처 : " + jobLauncher.toString());
        System.out.println("JobApplicationRunner 잡 파라미터 : " + jobParameters.getParameters());
        System.out.println("JobApplicationRunner 잡 : " + job.getName());
		jobLauncher.run(job, jobParameters);
    }
}
