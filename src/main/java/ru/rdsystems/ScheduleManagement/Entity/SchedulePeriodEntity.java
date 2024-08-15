package ru.rdsystems.ScheduleManagement.Entity;

import jakarta.persistence.*;
import lombok.*;
import ru.rdsystems.ScheduleManagement.Common.Enums.SlotTypeEnum;

@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="schedule_period")
public class SchedulePeriodEntity {
    @Id
    @Column(nullable = false, length = 32)
    @NonNull
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "slot_id", referencedColumnName = "id", nullable = false)
    @NonNull
    private SlotEntity slotId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schedule_id", referencedColumnName = "id", nullable = false)
    @NonNull
    private ScheduleEntity scheduleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "slot_type", length = 20)
    @NonNull
    private SlotTypeEnum slotType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "administrator_id", referencedColumnName = "id", nullable = false)
    @NonNull
    private EmployeeEntity administratorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "executor_id", referencedColumnName = "id")
    private EmployeeEntity executorId;
}
