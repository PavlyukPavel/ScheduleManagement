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
import ru.rdsystems.schedule.dto.request.SlotPostRq;
import ru.rdsystems.schedule.entity.SlotEntity;
import ru.rdsystems.schedule.service.SlotService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/slot")
public class SlotController {
    private final SlotService slotService;

    @PostMapping("/post")
    public ResponseEntity<EntityPostRs> post(
            SlotPostRq slotPostRq,
            @RequestHeader(required = false) Map<String, Object> queryHeader,
            @RequestBody(required = false) Map<String, Object> queryBody
    ) {
        Utils.log("POST: Slot");
        Utils.log(slotPostRq);
        Utils.log(queryHeader);
        Utils.log(queryBody);

        SlotEntity s = slotService.post(slotPostRq);
        return ResponseEntity.ok(new EntityPostRs("OK", s.getId()));
    }

    @GetMapping("/get")
    public ResponseEntity<SlotEntity> get(
            EntityGetRq entityGetRq,
            @RequestHeader(required = false) Map<String, Object> queryHeader,
            @RequestBody(required = false) Map<String, Object> queryBody
    ) {
        Utils.log("GET: Slot");
        Utils.log(entityGetRq);
        Utils.log(queryHeader);
        Utils.log(queryBody);

        try {
            SlotEntity s = slotService.get(entityGetRq.getId());
            return ResponseEntity.ok(s);
        } catch (ScheduleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
