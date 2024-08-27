package ru.rdsystems.schedule.service;

import ru.rdsystems.schedule.entity.ScheduleEntity;

import java.util.List;
import java.util.Map;

public interface ScheduleService {
    ScheduleEntity post(
            String name
    );

    ScheduleEntity get(String id);

    List<Map<String, Object>> getSchedule(String id, String name);
}
