package ru.rdsystems.ScheduleManagement.API;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.rdsystems.ScheduleManagement.Common.Exceptions.ScheduleNotFoundException;
import ru.rdsystems.ScheduleManagement.DTO.Request.EntityGetRq;
import ru.rdsystems.ScheduleManagement.DTO.Response.EntityPostRs;
import ru.rdsystems.ScheduleManagement.DTO.Request.SlotPostRq;
import ru.rdsystems.ScheduleManagement.Entity.SlotEntity;
import ru.rdsystems.ScheduleManagement.Service.SlotService;

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
        System.out.println("POST: Slot");
        System.out.println(slotPostRq);
        System.out.println(queryHeader);
        System.out.println(queryBody);

        SlotEntity s = slotService.post(slotPostRq);
        return ResponseEntity.ok(new EntityPostRs("OK", s.getId()));
    }

    @GetMapping("/get")
    public ResponseEntity<SlotEntity> get(
            EntityGetRq entityGetRq,
            @RequestHeader(required = false) Map<String, Object> queryHeader,
            @RequestBody(required = false) Map<String, Object> queryBody
    ) {
        System.out.println("GET: Slot");
        System.out.println(entityGetRq);
        System.out.println(queryHeader);
        System.out.println(queryBody);

        try {
            SlotEntity s = slotService.get(entityGetRq.getId());
            return ResponseEntity.ok(s);
        } catch (ScheduleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
