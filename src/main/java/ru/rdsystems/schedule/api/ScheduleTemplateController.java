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
import ru.rdsystems.schedule.entity.ScheduleTemplateEntity;
import ru.rdsystems.schedule.service.ScheduleTemplateService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ScheduleTemplate")
public class ScheduleTemplateController {
    private final ScheduleTemplateService scheduleTemplateService;

    @PostMapping("/post")
    public ResponseEntity<EntityPostRs> post(
            @RequestParam(required = false) Map<String, Object> queryParam,
            @RequestHeader(required = false) Map<String, Object> queryHeader,
            @RequestBody(required = false) Map<String, Object> queryBody
    ) {
        Utils.log("POST: Schedule Template");
        Utils.log(queryParam);
        Utils.log(queryHeader);
        Utils.log(queryBody);

        ScheduleTemplateEntity s = scheduleTemplateService.post();
        return ResponseEntity.ok(new EntityPostRs("OK", s.getId()));
    }

    @GetMapping("/get")
    public ResponseEntity<ScheduleTemplateEntity> get(
            EntityGetRq entityGetRq,
            @RequestHeader(required = false) Map<String, Object> queryHeader,
            @RequestBody(required = false) Map<String, Object> queryBody
    ) {
        Utils.log("GET: Schedule Template");
        Utils.log(entityGetRq);
        Utils.log(queryHeader);
        Utils.log(queryBody);

        try {
            ScheduleTemplateEntity s = scheduleTemplateService.get(entityGetRq.getId());
            return ResponseEntity.ok(s);
        } catch (ScheduleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
