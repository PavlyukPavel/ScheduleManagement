package ru.rdsystems.schedule.service;

import ru.rdsystems.schedule.dto.request.SlotPostRq;
import ru.rdsystems.schedule.entity.SlotEntity;

public interface SlotService {
    SlotEntity post(SlotPostRq slotPostRq);

    SlotEntity get(String id);
}
