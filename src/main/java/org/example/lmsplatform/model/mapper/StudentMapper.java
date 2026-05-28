package org.example.lmsplatform.model.mapper;

import org.example.lmsplatform.model.dto.StudentDtoResponse;
import org.example.lmsplatform.model.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(source = "studentGroup.id", target = "studentGroupId")
    @Mapping(source = "studentGroup.name", target = "studentGroupName")
    StudentDtoResponse toDtoResponse(Student student);
}
