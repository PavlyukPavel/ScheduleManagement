package ru.rdsystems.ScheduleManagement.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rdsystems.ScheduleManagement.Common.Exceptions.ScheduleNotFoundException;
import ru.rdsystems.ScheduleManagement.Common.Helpers.IDGenerator;
import ru.rdsystems.ScheduleManagement.Entity.ScheduleTemplateEntity;
import ru.rdsystems.ScheduleManagement.Repository.ScheduleTemplateRepo;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class ScheduleTemplateService {

    @Autowired
    private final ScheduleTemplateRepo sheduleTemplateRepo;

    @Transactional
    public ScheduleTemplateEntity post() {
        ScheduleTemplateEntity s = ScheduleTemplateEntity.builder()
                .id(IDGenerator.generate())
                .created(OffsetDateTime.now())
                .build();

        sheduleTemplateRepo.save(s);
        return s;
    }

    public ScheduleTemplateEntity get(String id) {
        return sheduleTemplateRepo.findById(id).orElseThrow(()->new ScheduleNotFoundException(
                String.format("Шаблон расписания %s не найден", id))
        );
    }
}
