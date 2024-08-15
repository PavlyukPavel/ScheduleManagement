package ru.rdsystems.ScheduleManagement.Repository;

import ru.rdsystems.ScheduleManagement.Entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<EmployeeEntity, String> {
}
