package org.example.lmsplatform.model.dto;

import java.util.Set;


public record StudentGroupDtoResponse(
        Long id,
        String name,
        Set<Long> courseIds,
        Set<Long> studentIds,
        Set<Long> scheduleIds
) {
}
