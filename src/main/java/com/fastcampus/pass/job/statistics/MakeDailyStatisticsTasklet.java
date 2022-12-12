package com.fastcampus.pass.job.statistics;

import com.fastcampus.pass.utils.CustomCSVWriter;
import com.fastcampus.pass.repository.statistics.AggregatedStatistics;
import com.fastcampus.pass.repository.statistics.StatisticsRepository;
import com.fastcampus.pass.utils.LocalDateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class MakeDailyStatisticsTasklet implements Tasklet {

    @Value("#{jobParameters[from]}")
    private String fromString;
    @Value("#{jobParameters[to]")
    private String toString;
    private final StatisticsRepository statisticsRepository;


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        final LocalDateTime from = LocalDateTimeUtil.parse(fromString);
        final LocalDateTime to = LocalDateTimeUtil.parse(toString);

        final List<AggregatedStatistics> statisticsList = statisticsRepository.findByStatisticsAtBetweenAndGroupBy(from, to);

        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"statisticsAt", "allCount", "attendedCount", "cancelledCount"});
        for (AggregatedStatistics statistics : statisticsList) {
            data.add(new String[]{
                    LocalDateTimeUtil.format(statistics.getStatisticsAt()),
                    String.valueOf(statistics.getAllCount()),
                    String.valueOf(statistics.getAttendedCount()),
                    String.valueOf(statistics.getCancelledCount())
            });
        }

        CustomCSVWriter.write("daily_statistics" + LocalDateTimeUtil.format(from, LocalDateTimeUtil.YYYY_MM_DD) + ".csv", data);
        return RepeatStatus.FINISHED;
    }
}
