package ru.rdsystems.schedule.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.rdsystems.schedule.common.exceptions.ScheduleNotFoundException;
import ru.rdsystems.schedule.common.helpers.Utils;
import ru.rdsystems.schedule.dto.filtersort.SchedulePeriodFilterAndSort;
import ru.rdsystems.schedule.dto.request.EntityGetRq;
import ru.rdsystems.schedule.dto.response.EntityPostRs;
import ru.rdsystems.schedule.dto.request.SchedulePeriodPostRq;
import ru.rdsystems.schedule.entity.SchedulePeriodEntity;
import ru.rdsystems.schedule.service.SchedulePeriodService;

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
        Utils.log("POST: Schedule Period");
        Utils.log(schedulePeriodPostRq);
        Utils.log("administratorId = " + administratorId);
        Utils.log(queryBody);

        SchedulePeriodEntity s = schedulePeriodService.post(schedulePeriodPostRq, administratorId);
        return ResponseEntity.ok(new EntityPostRs("OK", s.getId()));
    }

    @GetMapping("/get")
    public ResponseEntity<SchedulePeriodEntity> get(
            EntityGetRq entityGetRq,
            @RequestHeader(required = false) Map<String, Object> queryHeader,
            @RequestBody(required = false) Map<String, Object> queryBody
    ) {
        Utils.log("GET: Schedule Period");
        Utils.log(entityGetRq);
        Utils.log(queryHeader);
        Utils.log(queryBody);

        try {
            SchedulePeriodEntity s = schedulePeriodService.get(entityGetRq.getId());
            return ResponseEntity.ok(s);
        } catch (ScheduleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<List<SchedulePeriodEntity>> filter(
            @RequestParam(required = false) Map<String, Object> queryParam,
            @RequestHeader(required = false) Map<String, Object> queryHeader,
            @RequestBody SchedulePeriodFilterAndSort filterAndSort
    ) {
        Utils.log("FILTER: Schedule Period");
        Utils.log(queryParam);
        Utils.log(queryHeader);
        Utils.log(filterAndSort);

        return ResponseEntity.ok(schedulePeriodService.filter(filterAndSort));
    }
}
