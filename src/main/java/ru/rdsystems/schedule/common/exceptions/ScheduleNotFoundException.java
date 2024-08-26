package ru.rdsystems.schedule.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}