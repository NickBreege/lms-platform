package org.example.lmsplatform.model.mapper;

import org.example.lmsplatform.model.dto.TeacherDtoResponse;
import org.example.lmsplatform.model.entity.Course;
import org.example.lmsplatform.model.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    @Mapping(source = "courses", target = "courseIds")
    TeacherDtoResponse toDtoResponse(Teacher teacher);

    default Set<Long> mapCourses(Set<Course> courses) {
        if (courses == null)
            return null;
        return courses.stream()
                .map(Course::getId)
                .collect(Collectors.toSet());
    }
}
