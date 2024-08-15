package ru.rdsystems.ScheduleManagement.Service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.rdsystems.ScheduleManagement.Common.Exceptions.ScheduleNotFoundException;
import ru.rdsystems.ScheduleManagement.Common.Helpers.IDGenerator;
import ru.rdsystems.ScheduleManagement.Common.Enums.PositionEnum;
import ru.rdsystems.ScheduleManagement.Common.Enums.StatusEnum;
import ru.rdsystems.ScheduleManagement.DTO.Request.EmployeePostRq;
import ru.rdsystems.ScheduleManagement.Entity.EmployeeEntity;
import ru.rdsystems.ScheduleManagement.Repository.EmployeeRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    @Autowired
    private final EmployeeRepo employeeRepo;

    @Transactional
    public EmployeeEntity post(EmployeePostRq employeePostRq) {
        EmployeeEntity emp = EmployeeEntity.builder()
                .id(IDGenerator.generate())
                .name(employeePostRq.getName())
                .status(StatusEnum.valueOf(employeePostRq.getStatus()))
                .position(PositionEnum.valueOf(employeePostRq.getPosition()))
                .build();
        employeeRepo.save(emp);
        return emp;
    }

    public EmployeeEntity get(String id) {
        return employeeRepo.findById(id).orElseThrow(()->new ScheduleNotFoundException(
                String.format("Сотрудник %s не найден", id))
        );
    }
}
