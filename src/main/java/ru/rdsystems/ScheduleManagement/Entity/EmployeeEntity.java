package ru.rdsystems.ScheduleManagement.Entity;

import ru.rdsystems.ScheduleManagement.Common.Enums.PositionEnum;
import ru.rdsystems.ScheduleManagement.Common.Enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="employee")
public class EmployeeEntity {
    @Id
    @Column(nullable = false, length = 32)
    @NonNull
    private String id;

    @Column(nullable = false, name = "employee_name", length = 255)
    @NonNull
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @NonNull
    private StatusEnum status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @NonNull
    private PositionEnum position;

    /*@JsonIgnore
    @OneToOne(mappedBy = "administratorId")
    private SchedulePeriodEntity schedulePeriodAdm*/

    /*@JsonIgnore
    @OneToOne(mappedBy = "executorId")
    private SchedulePeriodEntity schedulePeriodExec;*/
}
