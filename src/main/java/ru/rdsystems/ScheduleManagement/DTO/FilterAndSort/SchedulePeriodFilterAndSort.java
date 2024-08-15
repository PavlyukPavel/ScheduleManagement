package ru.rdsystems.ScheduleManagement.DTO.FilterAndSort;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SchedulePeriodFilterAndSort {
    SchedulePeriodFilter filter;
    List<SchedulePeriodSort> sort;
    Integer page;
    Integer size;
}
