package com.fastcampus.pass.repository.pass;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BulkPassRepository extends JpaRepository<BulkPassEntity, Integer> {
    // WHERE status = :status AND startedAt > :startedAt => status 와 일치하고, startedAt 보다 클 때(초과)
    List<BulkPassEntity> findByStatusAndStartedAtGreaterThan(BulkPassStatus status, LocalDateTime startedAt);
}
