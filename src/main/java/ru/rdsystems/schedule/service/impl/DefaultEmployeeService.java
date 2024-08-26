package ru.rdsystems.schedule.service.impl;

import ru.rdsystems.schedule.common.exceptions.ScheduleNotFoundException;
import ru.rdsystems.schedule.common.enums.PositionEnum;
import ru.rdsystems.schedule.common.enums.StatusEnum;
import ru.rdsystems.schedule.common.helpers.Utils;
import ru.rdsystems.schedule.dto.request.EmployeePostRq;
import ru.rdsystems.schedule.entity.EmployeeEntity;
import ru.rdsystems.schedule.repository.EmployeeRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rdsystems.schedule.service.EmployeeService;

@Service
@RequiredArgsConstructor
public class DefaultEmployeeService implements EmployeeService {

    private final EmployeeRepo employeeRepo;

    @Transactional @Override public EmployeeEntity post(EmployeePostRq employeePostRq) {
        EmployeeEntity emp = EmployeeEntity.builder()
                .id(Utils.generateID())
                .name(employeePostRq.getName())
                .status(StatusEnum.valueOf(employeePostRq.getStatus()))
                .position(PositionEnum.valueOf(employeePostRq.getPosition()))
                .build();
        employeeRepo.save(emp);
        return emp;
    }

    @Override public EmployeeEntity get(String id) {
        return employeeRepo.findById(id).orElseThrow(()->new ScheduleNotFoundException(
                String.format("Сотрудник %s не найден", id))
        );
    }

}
