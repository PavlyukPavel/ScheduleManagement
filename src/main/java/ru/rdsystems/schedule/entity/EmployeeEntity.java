package ru.rdsystems.schedule.entity;

import ru.rdsystems.schedule.common.enums.PositionEnum;
import ru.rdsystems.schedule.common.enums.StatusEnum;
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
}
