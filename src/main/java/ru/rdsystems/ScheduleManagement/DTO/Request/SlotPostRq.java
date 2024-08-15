package ru.rdsystems.ScheduleManagement.DTO.Request;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalTime;

@Data
public class SlotPostRq {
    @NonNull private String scheduleTemplateId;
    @NonNull private LocalTime beginTime;
    @NonNull private LocalTime endTime;
}
