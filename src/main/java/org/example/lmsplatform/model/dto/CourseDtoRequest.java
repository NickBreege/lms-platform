package org.example.lmsplatform.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseDtoRequest {

    @NotBlank(message = "Название курса обязательно")
    @Size(max = 100, message = "Название курса не должно превышать 100 символов")
    private String name;

    @NotBlank(message = "Описание курса обязательно")
    @Size(max = 100, message = "Описание курса не должно превышать 100 символов")
    private String description;

    @NotNull(message = "Id преподавателя обязателен")
    @Positive(message = "Id преподавателя должен быть положительным")
    private Long teacherId;
}
