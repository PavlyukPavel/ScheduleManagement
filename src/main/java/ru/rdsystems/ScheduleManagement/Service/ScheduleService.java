package ru.rdsystems.ScheduleManagement.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rdsystems.ScheduleManagement.Common.Exceptions.ScheduleNotFoundException;
import ru.rdsystems.ScheduleManagement.Common.Helpers.IDGenerator;
import ru.rdsystems.ScheduleManagement.Entity.ScheduleEntity;
import ru.rdsystems.ScheduleManagement.Repository.ScheduleRepo;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    @Autowired
    private final ScheduleRepo scheduleRepo;

    @Transactional
    public ScheduleEntity post(
            String name
    ) {
        ScheduleEntity s = ScheduleEntity.builder()
                .id(IDGenerator.generate())
                .name(name)
                .created(OffsetDateTime.now())
                .build();
        scheduleRepo.save(s);
        return s;
    }

    public ScheduleEntity get(String id) {
        return scheduleRepo.findById(id).orElseThrow(()->new ScheduleNotFoundException(
                String.format("Расписание %s не найдено", id))
        );
    }
}
