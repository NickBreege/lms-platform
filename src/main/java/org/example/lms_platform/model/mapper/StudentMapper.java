package org.example.lms_platform.model.mapper;

import org.example.lms_platform.model.dto.StudentDtoRs;
import org.example.lms_platform.model.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(source = "group.id", target = "groupId")
    StudentDtoRs toDtoRs(Student student);

    @Mapping(source = "groupId", target = "group.id")
    Student toEntity(StudentDtoRs dto);
}
