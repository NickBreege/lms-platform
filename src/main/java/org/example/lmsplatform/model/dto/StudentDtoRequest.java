package org.example.lmsplatform.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentDtoRequest {
    @NotBlank(message = "Имя студента обязательно")
    @Size(max = 100, message = "Имя студента не должно превышать 100 символов")
    private String name;

    @NotBlank(message = "Фамилия студента обязательна")
    @Size(max = 100, message = "Фамилия студента не должна превышать 100 символов")
    private String surname;

    @NotNull(message = "Id группы обязателен")
    @Positive(message = "Id группы должен быть положительным")
    private Long studentGroupId;
}
