package ru.rdsystems.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rdsystems.schedule.entity.ScheduleTemplateEntity;

@Repository
public interface ScheduleTemplateRepo extends JpaRepository<ScheduleTemplateEntity, String> {
}
