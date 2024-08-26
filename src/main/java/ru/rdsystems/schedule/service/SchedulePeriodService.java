package ru.rdsystems.schedule.service;

import ru.rdsystems.schedule.dto.filtersort.SchedulePeriodFilterAndSort;
import ru.rdsystems.schedule.dto.request.SchedulePeriodPostRq;
import ru.rdsystems.schedule.entity.SchedulePeriodEntity;

import java.util.List;

public interface SchedulePeriodService {
    SchedulePeriodEntity post(SchedulePeriodPostRq rq, String administratorId);

    SchedulePeriodEntity get(String id);

    List<SchedulePeriodEntity> filter(SchedulePeriodFilterAndSort filterAndSort);
}
