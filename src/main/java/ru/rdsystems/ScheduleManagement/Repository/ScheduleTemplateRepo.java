package ru.rdsystems.ScheduleManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rdsystems.ScheduleManagement.Entity.ScheduleTemplateEntity;

@Repository
public interface ScheduleTemplateRepo extends JpaRepository<ScheduleTemplateEntity, String> {
}
