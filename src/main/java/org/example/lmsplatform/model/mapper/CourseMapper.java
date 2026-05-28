package org.example.lmsplatform.model.mapper;

import org.example.lmsplatform.model.dto.CourseDtoResponse;
import org.example.lmsplatform.model.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {


    @Mapping(source = "teacher.id", target = "teacherId")
    @Mapping(source = "teacher.name", target = "teacherName")
    CourseDtoResponse toDtoResponse(Course course);
}
