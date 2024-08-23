package ru.rdsystems.ScheduleManagement.DTO.FilterAndSort;

import lombok.*;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SchedulePeriodFilter {
    private String id;
    private String slotId;
    private String scheduleId;
    private String slotType;
    private String administratorId;
    private String executorId;
    private LocalTime beginTime;
    private LocalTime endTime;
}
