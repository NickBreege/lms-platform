package org.example.lmsplatform.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ScheduleDtoRequest {

    @NotNull(message = "Id группы обязателен")
    @Positive(message = "Id группы должен быть положительным")
    private Long studentGroupId;

    @NotNull(message = "Id курса обязателен")
    @Positive(message = "Id курса должен быть положительным")
    private Long courseId;

    @NotNull(message = "Время начала занятия не может быть null")
    @Future(message = "Время начала должно быть в будущем")
    private LocalDateTime lessonStart;

    @NotNull(message = "Время окончания занятия не может быть null")
    @Future(message = "Время окончания должно быть в будущем")
    private LocalDateTime lessonEnd;
}
