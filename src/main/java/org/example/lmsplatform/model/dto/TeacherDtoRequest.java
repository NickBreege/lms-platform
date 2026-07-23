package org.example.lmsplatform.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeacherDtoRequest {
    @NotBlank(message = "Имя учителя обязательно")
    @Size(max = 100, message = "Имя учителя не должно превышать 100 символов")
    private String name;

    @NotBlank(message = "Фамилия учителя обязательна")
    @Size(max = 100, message = "Фамилия учителя не должна превышать 100 символов")
    private String surname;
}
