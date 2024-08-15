package ru.rdsystems.ScheduleManagement.API;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.rdsystems.ScheduleManagement.Common.Exceptions.ScheduleNotFoundException;
import ru.rdsystems.ScheduleManagement.DTO.FilterAndSort.SchedulePeriodFilterAndSort;
import ru.rdsystems.ScheduleManagement.DTO.Request.EntityGetRq;
import ru.rdsystems.ScheduleManagement.DTO.Response.EntityPostRs;
import ru.rdsystems.ScheduleManagement.DTO.Request.SchedulePeriodPostRq;
import ru.rdsystems.ScheduleManagement.Entity.SchedulePeriodEntity;
import ru.rdsystems.ScheduleManagement.Service.SchedulePeriodService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("SchedulePeriod")
public class SchedulePeriodController {
    private final SchedulePeriodService schedulePeriodService;

    @PostMapping("/post")
    public ResponseEntity<EntityPostRs> post(
            SchedulePeriodPostRq schedulePeriodPostRq,
            @RequestHeader(name = "x-current-user") String administratorId,
            @RequestBody(required = false) Map<String, Object> queryBody
    ) {
        System.out.println("POST: Schedule Period");
        System.out.println(schedulePeriodPostRq);
        System.out.println("administratorId = " + administratorId);
        System.out.println(queryBody);

        SchedulePeriodEntity s = schedulePeriodService.post(schedulePeriodPostRq, administratorId);
        return ResponseEntity.ok(new EntityPostRs("OK", s.getId()));
    }

    @GetMapping("/get")
    public ResponseEntity<SchedulePeriodEntity> get(
            EntityGetRq entityGetRq,
            @RequestHeader(required = false) Map<String, Object> queryHeader,
            @RequestBody(required = false) Map<String, Object> queryBody
    ) {
        System.out.println("GET: Schedule Period");
        System.out.println(entityGetRq);
        System.out.println(queryHeader);
        System.out.println(queryBody);

        try {
            SchedulePeriodEntity s = schedulePeriodService.get(entityGetRq.getId());
            return ResponseEntity.ok(s);
        } catch (ScheduleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<List<SchedulePeriodEntity>> get(
            @RequestParam(required = false) Map<String, Object> queryParam,
            @RequestHeader(required = false) Map<String, Object> queryHeader,
            @RequestBody SchedulePeriodFilterAndSort filterAndSort
    ) {
        System.out.println("FILTER: Schedule Period");
        System.out.println(queryParam);
        System.out.println(queryHeader);
        System.out.println(filterAndSort);

        return ResponseEntity.ok(schedulePeriodService.filter(filterAndSort));
    }
}
