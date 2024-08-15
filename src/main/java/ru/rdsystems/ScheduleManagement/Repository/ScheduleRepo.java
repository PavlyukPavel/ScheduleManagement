package ru.rdsystems.ScheduleManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rdsystems.ScheduleManagement.Entity.ScheduleEntity;

public interface ScheduleRepo extends JpaRepository<ScheduleEntity, String> {
}
