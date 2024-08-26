package ru.rdsystems.schedule.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rdsystems.schedule.common.exceptions.ScheduleNotFoundException;
import ru.rdsystems.schedule.common.helpers.DateTimeConverter;
import ru.rdsystems.schedule.common.helpers.Utils;
import ru.rdsystems.schedule.dto.request.SlotPostRq;
import ru.rdsystems.schedule.entity.ScheduleTemplateEntity;
import ru.rdsystems.schedule.entity.SlotEntity;
import ru.rdsystems.schedule.repository.SlotRepo;
import ru.rdsystems.schedule.service.ScheduleTemplateService;
import ru.rdsystems.schedule.service.SlotService;

@Service
@RequiredArgsConstructor
public class DefaultSlotService implements SlotService {

    private final SlotRepo slotRepo;
    private final ScheduleTemplateService scheduleTemplateService;

    @Transactional @Override public SlotEntity post(SlotPostRq slotPostRq) {
        ScheduleTemplateEntity scheduleTemplateEntity = scheduleTemplateService.get(slotPostRq.getScheduleTemplateId());

        SlotEntity se = SlotEntity.builder()
                .id(Utils.generateID())
                .scheduleTemplate(scheduleTemplateEntity)
                .beginTime(DateTimeConverter.toOffsetTime(slotPostRq.getBeginTime()))
                .endTime(DateTimeConverter.toOffsetTime(slotPostRq.getEndTime()))
                .build();

        slotRepo.save(se);
        return se;
    }

    @Override public SlotEntity get(String id) {
        return slotRepo.findById(id).orElseThrow(()->new ScheduleNotFoundException(
                String.format("Слот %s не найден", id))
        );
    }
}
