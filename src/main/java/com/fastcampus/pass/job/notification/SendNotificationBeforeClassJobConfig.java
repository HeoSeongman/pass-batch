package com.fastcampus.pass.job.notification;

import com.fastcampus.pass.repository.booking.BookingEntity;
import com.fastcampus.pass.repository.booking.BookingStatus;
import com.fastcampus.pass.repository.notification.NotificationEntity;
import com.fastcampus.pass.repository.notification.NotificationEvent;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.batch.item.support.builder.SynchronizedItemStreamReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.security.PublicKey;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class SendNotificationBeforeClassJobConfig extends DefaultBatchConfiguration {

    private final int CHUNK_SIZE = 10;

    private final EntityManagerFactory entityManagerFactory;
    private final SendNotificationItemWriter sendNotificationItemWriter;

    @Bean
    public Job sendNotificationBeforeClassJob() {
        return new JobBuilder("sendNotificationBeforeClassJob", jobRepository())
                .start(addNotificationStep())
                .next(sendNotificationStep())
                .build();
    }

    @Bean
    public Step addNotificationStep() {
        return new StepBuilder("addNotificationStep", jobRepository())
                .<BookingEntity, NotificationEntity> chunk(CHUNK_SIZE, getTransactionManager())
                .reader(addNotificationItemReader())
                .processor(addNotificationProcessor())
                .writer(addNotificationWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<BookingEntity> addNotificationItemReader() {
        // ????????? ???????????????, ??????????????? 10??? ??? ???????????? ????????? ?????? ??????
        return new JpaPagingItemReaderBuilder<BookingEntity>()
                .name("addNotificationItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(CHUNK_SIZE)
                .queryString("select b from BookingEntity b join fetch b.userEntity where b.status = :status and b.startedAt <= :startedAt order by b.bookingSeq")
                .build();
    }

    @Bean
    public ItemProcessor<BookingEntity, NotificationEntity> addNotificationProcessor() {
        return bookingEntity -> bookingEntity.toNotificationEntity(NotificationEvent.BEFORE_CLASS);
    }

    @Bean
    public JpaItemWriter<NotificationEntity> addNotificationWriter() {
        return new JpaItemWriterBuilder<NotificationEntity>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Step sendNotificationStep() {
        return new StepBuilder("sendNotificationStep", jobRepository())
                .<NotificationEntity, NotificationEntity> chunk(CHUNK_SIZE, getTransactionManager())
                .reader(sendNotificationItemReader())
                .writer(sendNotificationItemWriter)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();

    }

    @Bean
    public SynchronizedItemStreamReader<NotificationEntity> sendNotificationItemReader() {
        // ???????????? ?????? ?????????, ?????? ????????? ???????????? ????????? ?????? ??????
        JpaCursorItemReader<NotificationEntity> reader = new JpaCursorItemReaderBuilder<NotificationEntity>()
                .name("sendNotificationItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select n from NotificationEntity n where n.event = :event and n.sent = :sent")
                .parameterValues(Map.of("event", NotificationEvent.BEFORE_CLASS, "sent", false))
                .build();

        return new SynchronizedItemStreamReaderBuilder<NotificationEntity>()
                .delegate(reader)
                .build();
    }
}
