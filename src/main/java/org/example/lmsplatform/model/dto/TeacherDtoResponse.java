package org.example.lmsplatform.model.dto;

import java.util.Set;

public record TeacherDtoResponse(
        Long id,
        String name,
        String surname,
        Set<Long> courseIds
) {
}
