package com.fastcampus.pass.repository.notification;

import com.fastcampus.pass.repository.BaseEntity;
import com.fastcampus.pass.utils.LocalDateTimeUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "notification")
@NoArgsConstructor
public class NotificationEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationSeq;
    private String uuid;

    private NotificationEvent event;
    private String text;
    private boolean sent;
    private LocalDateTime sendAt;

    private NotificationEntity(String uuid, NotificationEvent event, String text) {
        this.uuid = uuid;
        this.event = event;
        this.text = text;
    }

    public static NotificationEntity of(String uuid, NotificationEvent event, LocalDateTime startedAt) {
        String text = String.format("안녕하세요. %s 수업 시작합니다. 수업 전 출석 확인 부탁드립니다. \uD83D\uDE0A", LocalDateTimeUtil.format(startedAt));
        return new NotificationEntity(uuid, event, text);
    }
}
