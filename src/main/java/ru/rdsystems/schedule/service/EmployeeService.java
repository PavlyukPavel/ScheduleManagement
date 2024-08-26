package ru.rdsystems.schedule.service;

import ru.rdsystems.schedule.dto.request.EmployeePostRq;
import ru.rdsystems.schedule.entity.EmployeeEntity;

public interface EmployeeService {
    EmployeeEntity post(EmployeePostRq employeePostRq);

    EmployeeEntity get(String id);
}
