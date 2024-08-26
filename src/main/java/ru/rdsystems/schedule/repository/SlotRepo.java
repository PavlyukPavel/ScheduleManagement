package ru.rdsystems.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rdsystems.schedule.entity.SlotEntity;

@Repository
public interface SlotRepo extends JpaRepository<SlotEntity, String> {
}
