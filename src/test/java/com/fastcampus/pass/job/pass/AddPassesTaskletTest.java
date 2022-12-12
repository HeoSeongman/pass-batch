package com.fastcampus.pass.job.pass;

import com.fastcampus.pass.repository.pass.*;
import com.fastcampus.pass.repository.user.UserGroupMappingEntity;
import com.fastcampus.pass.repository.user.UserGroupMappingRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class) //JUnit 5
public class AddPassesTaskletTest {

    @Mock
    private StepContribution stepContribution;
    @Mock
    private ChunkContext chunkContext;
    @Mock
    private PassRepository passRepository;
    @Mock
    private BulkPassRepository bulkPassRepository;
    @Mock
    private UserGroupMappingRepository userGroupMappingRepository;

    // @InjectMocks 의 클래스를 생성하고 @Mock 으로 생성된 객체를 주입한다.
    // @InjectMocks 의 내부에서 필요한 객체들을 @Mock 으로 선언해야한다.
    @InjectMocks
    private AddPassesTasklet addPassesTasklet;

    @Test
    void test_execute() throws Exception {
        final String userGroupId = "GROUP";
        final String userId = "A100000";
        final Integer packageSeq = 1;
        final Integer count = 10;

        final LocalDateTime now = LocalDateTime.now();

        final BulkPassEntity bulkPassEntity = new BulkPassEntity();
        bulkPassEntity.setPackageSeq(packageSeq);
        bulkPassEntity.setUserGroupId(userGroupId);
        bulkPassEntity.setStatus(BulkPassStatus.READY);
        bulkPassEntity.setCount(count);
        bulkPassEntity.setStartedAt(now.minusDays(60));
        bulkPassEntity.setEndedAt(now);

        final UserGroupMappingEntity userGroupMappingEntity = new UserGroupMappingEntity();
        userGroupMappingEntity.setUserId(userId);
        userGroupMappingEntity.setUserGroupId(userGroupId);

        when(bulkPassRepository.findByStatusAndStartedAtGreaterThan(eq(BulkPassStatus.READY), any())).thenReturn(List.of(bulkPassEntity));
        when(userGroupMappingRepository.findByUserGroupId(eq("GROUP"))).thenReturn(List.of(userGroupMappingEntity));

        RepeatStatus repeatStatus = addPassesTasklet.execute(stepContribution, chunkContext);

        assertEquals(RepeatStatus.FINISHED, repeatStatus);

        ArgumentCaptor<List> passEntitiesCaptor = ArgumentCaptor.forClass(List.class);
        verify(passRepository, times(1)).saveAll(passEntitiesCaptor.capture());
        final List<PassEntity> passEntities = passEntitiesCaptor.getValue();

        assertEquals(1, passEntities.size());

        final PassEntity passEntity = passEntities.get(0);
        assertEquals(packageSeq, passEntity.getPackageSeq());
        assertEquals(userId, passEntity.getUserId());
        assertEquals(PassStatus.READY, passEntity.getStatus());
        assertEquals(count, passEntity.getRemainingCount());

    }

}
