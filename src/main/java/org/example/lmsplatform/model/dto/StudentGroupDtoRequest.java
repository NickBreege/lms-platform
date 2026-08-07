package org.example.lmsplatform.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StudentGroupDtoRequest(
        @NotBlank(message = "Невалидное имя группы")
        @Size(max = 100, message = "Название группы не должно превышать 100 символов")
        String name
) {
}
