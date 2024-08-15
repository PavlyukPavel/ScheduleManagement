package ru.rdsystems.ScheduleManagement.API;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.rdsystems.ScheduleManagement.Common.Exceptions.ScheduleNotFoundException;
import ru.rdsystems.ScheduleManagement.DTO.Request.EntityGetRq;
import ru.rdsystems.ScheduleManagement.DTO.Response.EntityPostRs;
import ru.rdsystems.ScheduleManagement.Entity.ScheduleTemplateEntity;
import ru.rdsystems.ScheduleManagement.Service.ScheduleTemplateService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ScheduleTemplate")
public class ScheduleTemplateController {
    private final ScheduleTemplateService sheduleTemplateService;

    @PostMapping("/post")
    public ResponseEntity<EntityPostRs> post(
            @RequestParam(required = false) Map<String, Object> queryParam,
            @RequestHeader(required = false) Map<String, Object> queryHeader,
            @RequestBody(required = false) Map<String, Object> queryBody
    ) {
        System.out.println("POST: Schedule Template");
        System.out.println(queryParam);
        System.out.println(queryHeader);
        System.out.println(queryBody);

        ScheduleTemplateEntity s = sheduleTemplateService.post();
        return ResponseEntity.ok(new EntityPostRs("OK", s.getId()));
    }

    @GetMapping("/get")
    public ResponseEntity<ScheduleTemplateEntity> get(
            EntityGetRq entityGetRq,
            @RequestHeader(required = false) Map<String, Object> queryHeader,
            @RequestBody(required = false) Map<String, Object> queryBody
    ) {
        System.out.println("GET: Schedule Template");
        System.out.println(entityGetRq);
        System.out.println(queryHeader);
        System.out.println(queryBody);

        try {
            ScheduleTemplateEntity s = sheduleTemplateService.get(entityGetRq.getId());
            return ResponseEntity.ok(s);
        } catch (ScheduleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
