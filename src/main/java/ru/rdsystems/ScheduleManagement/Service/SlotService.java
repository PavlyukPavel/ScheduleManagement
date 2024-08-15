package ru.rdsystems.ScheduleManagement.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rdsystems.ScheduleManagement.Common.Exceptions.ScheduleNotFoundException;
import ru.rdsystems.ScheduleManagement.Common.Helpers.DateTimeConverter;
import ru.rdsystems.ScheduleManagement.Common.Helpers.IDGenerator;
import ru.rdsystems.ScheduleManagement.DTO.Request.SlotPostRq;
import ru.rdsystems.ScheduleManagement.Entity.ScheduleTemplateEntity;
import ru.rdsystems.ScheduleManagement.Entity.SlotEntity;
import ru.rdsystems.ScheduleManagement.Repository.SlotRepo;

@Service
@RequiredArgsConstructor
public class SlotService {

    @Autowired
    private final SlotRepo slotRepo;
    @Autowired
    private final ScheduleTemplateService scheduleTemplateService;

    @Transactional
    public SlotEntity post(SlotPostRq slotPostRq) {
        ScheduleTemplateEntity scheduleTemplateEntity = scheduleTemplateService.get(slotPostRq.getScheduleTemplateId());

        SlotEntity se = SlotEntity.builder()
                .id(IDGenerator.generate())
                .scheduleTemplate(scheduleTemplateEntity)
                .beginTime(DateTimeConverter.toOffsetTime(slotPostRq.getBeginTime()))
                .endTime(DateTimeConverter.toOffsetTime(slotPostRq.getEndTime()))
                .build();

        slotRepo.save(se);
        return se;
    }

    public SlotEntity get(String id) {
        return slotRepo.findById(id).orElseThrow(()->new ScheduleNotFoundException(
                String.format("Слот %s не найден", id))
        );
    }
}
