package ru.rdsystems.schedule.service;

import ru.rdsystems.schedule.entity.ScheduleTemplateEntity;

public interface ScheduleTemplateService {

    ScheduleTemplateEntity post();

    ScheduleTemplateEntity get(String id);
}
