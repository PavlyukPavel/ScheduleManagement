package ru.rdsystems.schedule.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetTime;

@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="slot")
public class SlotEntity {
    @Id
    @Column(nullable = false, length = 32)
    @NonNull
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schedule_template_id", referencedColumnName = "id", nullable = false)
    @NonNull
    private ScheduleTemplateEntity scheduleTemplate;

    @Column(nullable = false, name = "begin_time")
    @NonNull
    private OffsetTime beginTime;

    @Column(nullable = false, name = "end_time")
    @NonNull
    private OffsetTime endTime;
}