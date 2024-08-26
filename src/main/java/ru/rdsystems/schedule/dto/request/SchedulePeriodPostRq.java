package ru.rdsystems.schedule.dto.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class SchedulePeriodPostRq {
    @NonNull private String slotId;
    @NonNull private String scheduleId;
    @NonNull private String slotType;
    private String executorId;
}
