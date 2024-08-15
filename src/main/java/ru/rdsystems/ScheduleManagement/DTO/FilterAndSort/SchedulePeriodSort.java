package ru.rdsystems.ScheduleManagement.DTO.FilterAndSort;

import lombok.*;
import org.springframework.data.domain.Sort;
import ru.rdsystems.ScheduleManagement.Common.Enums.ShedulePeriodColumnsEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SchedulePeriodSort {
    private ShedulePeriodColumnsEnum field;
    private Sort.Direction direction;
}
