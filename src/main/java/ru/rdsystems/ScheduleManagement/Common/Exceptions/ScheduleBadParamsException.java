package ru.rdsystems.ScheduleManagement.Common.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ScheduleBadParamsException extends RuntimeException {
    public ScheduleBadParamsException(String errorMessage) {
        super(errorMessage);
    }
}
