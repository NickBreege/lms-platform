package org.example.lmsplatform.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentGroupDtoRequest {
    @NotBlank(message = "Невалидное имя группы")
    @Size(max = 100, message = "Название группы не должно превышать 100 символов")
    private String name;
}
