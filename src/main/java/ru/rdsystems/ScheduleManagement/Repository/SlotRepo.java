package ru.rdsystems.ScheduleManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rdsystems.ScheduleManagement.Entity.SlotEntity;

@Repository
public interface SlotRepo extends JpaRepository<SlotEntity, String> {
}
