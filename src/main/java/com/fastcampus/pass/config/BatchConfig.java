package com.fastcampus.pass.config;

import com.fastcampus.pass.PassBatchApplication;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.BatchConfigurationException;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

@Slf4j
@Configuration
public class BatchConfig extends DefaultBatchConfiguration {

//  private final JobBuilderFactory jobBuilderFactory;
//	private final StepBuilderFactory stepBuilderFactory;

//	@Bean
//	public Step passStep() {
//		return this.stepBuilderFactory.get("passStep")
//				.tasklet((contribution, chunkContext) -> {
//					System.out.println("Execute PassStep");
//					return RepeatStatus.FINISHED;
//				}).build();
//	}
//
//	@Bean
//	public Job passJob() {
//		return this.jobBuilderFactory.get("passJob")
//				.start(passStep())
//				.build();
//	}

	@Bean
	public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("step", jobRepository)
				.tasklet((contribution, chunkContext) -> {
					System.out.println("======================================================================");
					System.out.println("실행됨");
					log.debug("================================================SL4J");
					return RepeatStatus.FINISHED;
				}, transactionManager).build();
	}

	@Bean
	public Job myJob(JobRepository jobRepository, Step passStep) {
		return new JobBuilder("myJob7", jobRepository)
				.start(passStep)
				.build();
	}
}
