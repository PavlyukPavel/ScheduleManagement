package ru.rdsystems.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.rdsystems.schedule.entity.SchedulePeriodEntity;

import java.time.OffsetTime;
import java.util.List;

@Repository
public interface SchedulePeriodRepo extends JpaRepository<SchedulePeriodEntity, String>, JpaSpecificationExecutor<SchedulePeriodEntity> {
    @Query(value = """
            select sp.*
            from schedule_period sp
            join slot s
            on sp.slot_id = s.id
            where sp.executor_id  = :employeeID
            and sp.schedule_id = :scheduleId
            and s.begin_time  < :endTime
            and s.end_time  > :beginTime""",
            nativeQuery = true)
    List<SchedulePeriodEntity> getIntersectPeriods(String employeeID, String scheduleId, OffsetTime beginTime, OffsetTime endTime);
}
