package org.example.lmsplatform.exception;

import lombok.experimental.StandardException;

@StandardException
public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException(Long scheduleId) {
        super("Расписание не найдено по id: " + scheduleId);
    }
}
