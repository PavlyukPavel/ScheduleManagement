package ru.rdsystems.schedule.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rdsystems.schedule.common.exceptions.ScheduleNotFoundException;
import ru.rdsystems.schedule.common.helpers.Utils;
import ru.rdsystems.schedule.entity.ScheduleEntity;
import ru.rdsystems.schedule.repository.ScheduleRepo;
import ru.rdsystems.schedule.service.ScheduleService;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class DefaultScheduleService implements ScheduleService {

    private final ScheduleRepo scheduleRepo;

    @Transactional @Override public ScheduleEntity post(
            String name
    ) {
        ScheduleEntity s = ScheduleEntity.builder()
                .id(Utils.generateID())
                .name(name)
                .created(OffsetDateTime.now())
                .build();
        scheduleRepo.save(s);
        return s;
    }

    @Override public ScheduleEntity get(String id) {
        return scheduleRepo.findById(id).orElseThrow(()->new ScheduleNotFoundException(
                String.format("Расписание %s не найдено", id))
        );
    }
}
