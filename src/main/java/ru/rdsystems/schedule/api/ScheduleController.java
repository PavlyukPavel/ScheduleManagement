package ru.rdsystems.schedule.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.rdsystems.schedule.common.exceptions.ScheduleNotFoundException;
import ru.rdsystems.schedule.common.helpers.Utils;
import ru.rdsystems.schedule.dto.request.EntityGetRq;
import ru.rdsystems.schedule.dto.response.EntityPostRs;
import ru.rdsystems.schedule.entity.ScheduleEntity;
import ru.rdsystems.schedule.service.ScheduleService;

import java.util.List;
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
        Utils.log("POST: Schedule");
        Utils.log("name = " + name);
        Utils.log(queryHeader);
        Utils.log(queryBody);

        ScheduleEntity s = scheduleService.post(name);

        return ResponseEntity.ok(new EntityPostRs("OK", s.getId()));
    }

    @GetMapping("/get")
    public ResponseEntity<ScheduleEntity> get(
            EntityGetRq entityGetRq,
            @RequestHeader(required = false) Map<String, Object> queryHeader,
            @RequestBody(required = false) Map<String, Object> queryBody
    ) {
        Utils.log("GET: Schedule");
        Utils.log(entityGetRq);
        Utils.log(queryHeader);
        Utils.log(queryBody);

        try {
            ScheduleEntity s = scheduleService.get(entityGetRq.getId());
            return ResponseEntity.ok(s);
        } catch (ScheduleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/full")
    public ResponseEntity<List<Map<String, Object>>> full(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name,
            @RequestHeader(required = false) Map<String, Object> queryHeader,
            @RequestBody(required = false) Map<String, Object> queryBody
    ) {
        Utils.log("FULL: Schedule");
        Utils.log("id = " + id);
        Utils.log("name = " + name);
        Utils.log(queryHeader);
        Utils.log(queryBody);

        List<Map<String, Object>> result = scheduleService.getSchedule(id, name);
        return ResponseEntity.ok(result);
    }
}
