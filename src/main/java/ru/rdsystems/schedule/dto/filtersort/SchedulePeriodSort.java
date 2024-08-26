package ru.rdsystems.schedule.dto.filtersort;

import lombok.*;
import org.springframework.data.domain.Sort;
import ru.rdsystems.schedule.common.enums.SchedulePeriodColumnsEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SchedulePeriodSort {
    private SchedulePeriodColumnsEnum field;
    private Sort.Direction direction;
}
