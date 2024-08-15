package ru.rdsystems.ScheduleManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.rdsystems.ScheduleManagement.Entity.SchedulePeriodEntity;

import java.util.ArrayList;

@Repository
public interface SchedulePeriodRepo extends JpaRepository<SchedulePeriodEntity, String>, JpaSpecificationExecutor<SchedulePeriodEntity> {
}
