package ru.rdsystems.schedule.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rdsystems.schedule.common.exceptions.ScheduleNotFoundException;
import ru.rdsystems.schedule.common.helpers.Utils;
import ru.rdsystems.schedule.entity.ScheduleTemplateEntity;
import ru.rdsystems.schedule.repository.ScheduleTemplateRepo;
import ru.rdsystems.schedule.service.ScheduleTemplateService;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class DefaultScheduleTemplateService implements ScheduleTemplateService {

    private final ScheduleTemplateRepo scheduleTemplateRepo;

    @Transactional @Override public ScheduleTemplateEntity post() {
        ScheduleTemplateEntity s = ScheduleTemplateEntity.builder()
                .id(Utils.generateID())
                .created(OffsetDateTime.now())
                .build();

        scheduleTemplateRepo.save(s);
        return s;
    }

    @Override public ScheduleTemplateEntity get(String id) {
        return scheduleTemplateRepo.findById(id).orElseThrow(()->new ScheduleNotFoundException(
                String.format("Шаблон расписания %s не найден", id))
        );
    }
}
