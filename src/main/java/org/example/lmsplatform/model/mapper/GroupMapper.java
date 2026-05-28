package org.example.lmsplatform.model.mapper;

import org.example.lmsplatform.model.dto.StudentGroupDtoResponse;
import org.example.lmsplatform.model.entity.Course;
import org.example.lmsplatform.model.entity.Schedule;
import org.example.lmsplatform.model.entity.Student;
import org.example.lmsplatform.model.entity.StudentGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    @Mapping(source = "courses", target = "courseIds")
    @Mapping(source = "students", target = "studentIds")
    @Mapping(source = "schedules", target = "scheduleIds")
    StudentGroupDtoResponse toDtoResponse(StudentGroup studentGroup);

    default Set<Long> mapCourses(Set<Course> courses) {
        if (courses == null)
            return null;
        return courses.stream()
                .map(Course::getId)
                .collect(Collectors.toSet());
    }

    default Set<Long> mapStudents(Set<Student> students) {
        if (students == null)
            return null;
        return students.stream()
                .map(Student::getId)
                .collect(Collectors.toSet());
    }

    default Set<Long> mapSchedules(Set<Schedule> schedules) {
        if (schedules == null)
            return null;
        return schedules.stream()
                .map(Schedule::getId)
                .collect(Collectors.toSet());
    }
}
