package ru.rdsystems.ScheduleManagement.API;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ru.rdsystems.ScheduleManagement.Common.Exceptions.ScheduleNotFoundException;
import ru.rdsystems.ScheduleManagement.DTO.Request.EmployeePostRq;
import ru.rdsystems.ScheduleManagement.DTO.Request.EntityGetRq;
import ru.rdsystems.ScheduleManagement.DTO.Response.EntityPostRs;
import ru.rdsystems.ScheduleManagement.Entity.EmployeeEntity;
import ru.rdsystems.ScheduleManagement.Service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/post")
    public ResponseEntity<EntityPostRs> post(
            EmployeePostRq employeePostRq,
            @RequestHeader(required = false) Map<String, Object> queryHeader,
            @RequestBody(required = false) Map<String, Object> queryBody
    ) {
        System.out.println("POST: employee");
        System.out.println(employeePostRq);
        System.out.println(queryHeader);
        System.out.println(queryBody);

        EmployeeEntity emp = employeeService.post(employeePostRq);

        return ResponseEntity.ok(new EntityPostRs("OK", emp.getId()));
    }

    @GetMapping("/get")
    public ResponseEntity<EmployeeEntity> get(
            EntityGetRq entityGetRq,
            @RequestHeader(required = false) Map<String, Object> queryHeader,
            @RequestBody(required = false) Map<String, Object> queryBody
    ) {
        System.out.println("GET: employee");
        System.out.println(entityGetRq);
        System.out.println(queryHeader);
        System.out.println(queryBody);

        try {
            EmployeeEntity emp = employeeService.get(entityGetRq.getId());
            return ResponseEntity.ok(emp);
        } catch (ScheduleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
