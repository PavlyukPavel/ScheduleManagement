package ru.rdsystems.schedule.service;

import ru.rdsystems.schedule.entity.ScheduleEntity;

public interface ScheduleService {
    ScheduleEntity post(
            String name
    );

    ScheduleEntity get(String id);
}
