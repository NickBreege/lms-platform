package org.example.lms_platform.model.mapper;

import org.example.lms_platform.model.entity.Schedule;
import org.example.lms_platform.model.dto.ScheduleDtoRs;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "course.id", target = "courseId")
    ScheduleDtoRs toDto(Schedule schedule);

    @Mapping(source = "groupId", target = "group.id")
    @Mapping(source = "courseId", target = "course.id")
    Schedule toEntity(ScheduleDtoRs dto);
}
