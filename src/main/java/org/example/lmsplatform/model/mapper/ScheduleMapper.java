package org.example.lmsplatform.model.mapper;

import org.example.lmsplatform.model.dto.ScheduleDtoResponse;
import org.example.lmsplatform.model.entity.Schedule;
import org.example.lmsplatform.model.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    @Mapping(source = "studentGroup.id", target = "studentGroupId")
    @Mapping(source = "studentGroup.name", target = "studentGroupName")

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.name", target = "courseName")

    @Mapping(source = "course.teacher.id", target = "teacherId")
    @Mapping(source = "course.teacher", target = "teacherFullName")
    ScheduleDtoResponse toDtoResponse(Schedule schedule);

    default String teacherToFullName(Teacher teacher) {
        if (teacher == null) {
            return null;
        }
        return teacher.getName() + " " + teacher.getSurname();
    }

}

