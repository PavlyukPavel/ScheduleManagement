package ru.rdsystems.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rdsystems.schedule.entity.ScheduleEntity;

public interface ScheduleRepo extends JpaRepository<ScheduleEntity, String> {
}
