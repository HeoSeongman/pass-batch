package com.fastcampus.pass.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
public class JobCommandLineRunner implements CommandLineRunner {

    private final JobLauncher jobLauncher;
    private final Job job;

    @Override
    public void run(String... args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", job.getName())
                .toJobParameters();
        System.out.println("JobCommandLineRunner 잡 파라미터 : " + jobParameters.getParameters());
        System.out.println("JobCommandLineRunner 잡 : " + job.getName());
        System.out.println("JobCommandLineRunner 잡 런처 : " + jobLauncher.toString());
        jobLauncher.run(job, jobParameters);
    }
}
