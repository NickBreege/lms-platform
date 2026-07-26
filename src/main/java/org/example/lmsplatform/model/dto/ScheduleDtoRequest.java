package org.example.lmsplatform.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record ScheduleDtoRequest(
        @NotNull(message = "Id группы обязателен")
        @Positive(message = "Id группы должен быть положительным")
        Long studentGroupId,

        @NotNull(message = "Id курса обязателен")
        @Positive(message = "Id курса должен быть положительным")
        Long courseId,

        @NotNull(message = "Время начала занятия не может быть null")
        @Future(message = "Время начала должно быть в будущем")
        LocalDateTime lessonStart,

        @NotNull(message = "Время окончания занятия не может быть null")
        @Future(message = "Время окончания должно быть в будущем")
        LocalDateTime lessonEnd
) {
}
