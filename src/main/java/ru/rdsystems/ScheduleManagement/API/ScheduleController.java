package ru.rdsystems.ScheduleManagement.API;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.rdsystems.ScheduleManagement.Common.Exceptions.ScheduleNotFoundException;
import ru.rdsystems.ScheduleManagement.DTO.Request.EntityGetRq;
import ru.rdsystems.ScheduleManagement.DTO.Response.EntityPostRs;
import ru.rdsystems.ScheduleManagement.Entity.ScheduleEntity;
import ru.rdsystems.ScheduleManagement.Service.ScheduleService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/post")
    public ResponseEntity<EntityPostRs> post(
            @RequestParam(value = "name", required = false) String name,
            @RequestHeader(required = false) Map<String, Object> queryHeader,
            @RequestBody(required = false) Map<String, Object> queryBody
    ) {
        System.out.println("POST: Schedule");
        System.out.println("name = " + name);
        System.out.println(queryHeader);
        System.out.println(queryBody);

        ScheduleEntity s = scheduleService.post(name);

        return ResponseEntity.ok(new EntityPostRs("OK", s.getId()));
    }

    @GetMapping("/get")
    public ResponseEntity<ScheduleEntity> get(
            EntityGetRq entityGetRq,
            @RequestHeader(required = false) Map<String, Object> queryHeader,
            @RequestBody(required = false) Map<String, Object> queryBody
    ) {
        System.out.println("GET: Schedule");
        System.out.println(entityGetRq);
        System.out.println(queryHeader);
        System.out.println(queryBody);

        try {
            ScheduleEntity s = scheduleService.get(entityGetRq.getId());
            return ResponseEntity.ok(s);
        } catch (ScheduleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
