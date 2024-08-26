package ru.rdsystems.schedule.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ru.rdsystems.schedule.common.exceptions.ScheduleNotFoundException;
import ru.rdsystems.schedule.common.helpers.Utils;
import ru.rdsystems.schedule.dto.request.EmployeePostRq;
import ru.rdsystems.schedule.dto.request.EntityGetRq;
import ru.rdsystems.schedule.dto.response.EntityPostRs;
import ru.rdsystems.schedule.entity.EmployeeEntity;
import ru.rdsystems.schedule.service.EmployeeService;
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
        Utils.log("POST: employee");
        Utils.log(employeePostRq);
        Utils.log(queryHeader);
        Utils.log(queryBody);

        EmployeeEntity emp = employeeService.post(employeePostRq);

        return ResponseEntity.ok(new EntityPostRs("OK", emp.getId()));
    }

    @GetMapping("/get")
    public ResponseEntity<EmployeeEntity> get(
            EntityGetRq entityGetRq,
            @RequestHeader(required = false) Map<String, Object> queryHeader,
            @RequestBody(required = false) Map<String, Object> queryBody
    ) {
        Utils.log("GET: employee");
        Utils.log(entityGetRq);
        Utils.log(queryHeader);
        Utils.log(queryBody);

        try {
            EmployeeEntity emp = employeeService.get(entityGetRq.getId());
            return ResponseEntity.ok(emp);
        } catch (ScheduleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
