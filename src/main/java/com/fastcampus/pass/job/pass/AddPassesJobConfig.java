package com.fastcampus.pass.job.pass;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class AddPassesJobConfig extends DefaultBatchConfiguration {

    private final AddPassesTasklet addPassesTasklet;

    public AddPassesJobConfig(AddPassesTasklet addPassesTasklet) {
        this.addPassesTasklet = addPassesTasklet;
    }

    @Bean
    public Job addPassesJob() {
        return new JobBuilder("addPassesJob", jobRepository())
                .start(addPassesStep())
                .build();
    }

    @Bean
    public Step addPassesStep() {
        return new StepBuilder("addPassesStep", jobRepository())
                .tasklet(addPassesTasklet, getTransactionManager())
                .build();
    }
}
