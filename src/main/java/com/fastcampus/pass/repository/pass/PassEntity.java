package com.fastcampus.pass.repository.pass;

import com.fastcampus.pass.repository.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Table(name = "pass")
@Entity
@NoArgsConstructor
public class PassEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer passSeq;
    private Integer packageSeq;
    private String userId;

    @Enumerated(EnumType.STRING)
    private PassStatus status;

    private Integer remainingCount;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private LocalDateTime expiredAt;

    private PassEntity(Integer passSeq, Integer packageSeq, String userId, PassStatus status, Integer remainingCount, LocalDateTime startedAt, LocalDateTime endedAt) {
        this.passSeq = passSeq;
        this.packageSeq = packageSeq;
        this.userId = userId;
        this.status = status;
        this.remainingCount = remainingCount;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }

    public static PassEntity of(Integer passSeq, Integer packageSeq, String userId, PassStatus status, Integer remainingCount, LocalDateTime startedAt, LocalDateTime endedAt) {
        return new PassEntity(passSeq, packageSeq, userId, status, remainingCount, startedAt, endedAt);
    }

}
