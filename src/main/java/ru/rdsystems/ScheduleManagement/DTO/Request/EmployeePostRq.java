package ru.rdsystems.ScheduleManagement.DTO.Request;

import lombok.Data;
import lombok.NonNull;

@Data
public class EmployeePostRq {
    @NonNull private String name;
    @NonNull private String status;
    @NonNull private String position;
}
