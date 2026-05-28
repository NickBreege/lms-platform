package org.example.lmsplatform.service;

import lombok.RequiredArgsConstructor;
import org.example.lmsplatform.dao.CourseRepository;
import org.example.lmsplatform.dao.GroupRepository;
import org.example.lmsplatform.dao.ScheduleRepository;
import org.example.lmsplatform.dao.TeacherRepository;
import org.example.lmsplatform.exception.CourseNotFoundException;
import org.example.lmsplatform.exception.GroupNotFoundException;
import org.example.lmsplatform.exception.ScheduleNotFoundException;
import org.example.lmsplatform.exception.TeacherNotFoundException;
import org.example.lmsplatform.model.dto.ScheduleDtoRequest;
import org.example.lmsplatform.model.dto.ScheduleDtoResponse;
import org.example.lmsplatform.model.entity.Course;
import org.example.lmsplatform.model.entity.Schedule;
import org.example.lmsplatform.model.entity.StudentGroup;
import org.example.lmsplatform.model.entity.Teacher;
import org.example.lmsplatform.model.mapper.ScheduleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final ScheduleMapper scheduleMapper;

    @Transactional
    public ScheduleDtoResponse createSchedule(ScheduleDtoRequest scheduleDtoRequest) {
        validateLessonTime(scheduleDtoRequest.getLessonTime());

        StudentGroup group = getStudentGroupOrThrow(scheduleDtoRequest.getStudentGroupId());
        Course course = getCourseOrThrow(scheduleDtoRequest.getCourseId());

        validateGroupAssignedToCourse(group, course);
        validateGroupTimeConflict(group.getId(), scheduleDtoRequest.getLessonTime());
        validateTeacherTimeConflict(course.getTeacher().getId(), scheduleDtoRequest.getLessonTime());

        Schedule schedule = new Schedule();

        fillScheduleFromDto(schedule, scheduleDtoRequest);

        return scheduleMapper.toDtoResponse(scheduleRepository.save(schedule));
    }

    @Transactional(readOnly = true)
    public List<ScheduleDtoResponse> getGroupSchedule(Long groupId) {
        StudentGroup group = getGroupOrThrow(groupId);

        return scheduleRepository.findByStudentGroupId(group.getId())
                .stream()
                .map(scheduleMapper::toDtoResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ScheduleDtoResponse> getTeacherSchedule(Long teacherId) {
        Teacher teacher = getTeacherOrThrow(teacherId);

        return scheduleRepository.findByCourseTeacherIdOrderByLessonTimeAsc(teacher.getId())
                .stream()
                .map(scheduleMapper::toDtoResponse)
                .toList();
    }

    @Transactional
    public ScheduleDtoResponse updateSchedule(Long scheduleId, ScheduleDtoRequest scheduleDtoRequest) {
        validateLessonTime(scheduleDtoRequest.getLessonTime());

        Schedule schedule = getScheduleOrThrow(scheduleId);
        StudentGroup group = getGroupOrThrow(scheduleDtoRequest.getStudentGroupId());
        Course course = getCourseOrThrow(scheduleDtoRequest.getCourseId());

        validateGroupAssignedToCourse(group, course);
        validateGroupTimeConflictForUpdate(group.getId(), scheduleDtoRequest.getLessonTime(), schedule.getId());
        validateTeacherTimeConflictForUpdate(
                course.getTeacher().getId(),
                scheduleDtoRequest.getLessonTime(),
                schedule.getId());

        fillScheduleFromDto(schedule, scheduleDtoRequest);

        return scheduleMapper.toDtoResponse(schedule);
    }

    @Transactional
    public void deleteSchedule(Long scheduleId) {
        Schedule schedule = getScheduleOrThrow(scheduleId);

        scheduleRepository.delete(schedule);
    }


    private Teacher getTeacherOrThrow(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new TeacherNotFoundException(teacherId));
    }

    private Course getCourseOrThrow(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));
    }

    private StudentGroup getStudentGroupOrThrow(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));
    }

    private StudentGroup getGroupOrThrow(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));
    }

    private Schedule getScheduleOrThrow(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException(scheduleId));
    }

    private void validateLessonTime(LocalDateTime lessonTime) {
        if (lessonTime == null) {
            throw new IllegalArgumentException("Время занятия не может быть null");
        }
    }

    private void validateGroupTimeConflict(Long groupId, LocalDateTime lessonTime) {
        boolean exists = scheduleRepository.existsByStudentGroupIdAndLessonTime(groupId, lessonTime);

        if (exists) {
            throw new IllegalArgumentException("У группы уже есть занятие в это время");
        }
    }

    private void validateTeacherTimeConflict(Long teacherId, LocalDateTime lessonTime) {
        boolean exists = scheduleRepository.existsByCourseTeacherIdAndLessonTime(teacherId, lessonTime);

        if (exists) {
            throw new IllegalArgumentException("У преподавателя уже есть занятие в это время");
        }
    }

    private void validateGroupAssignedToCourse(StudentGroup studentGroup, Course course) {
        if (!studentGroup.getCourses().contains(course)) {
            throw new IllegalArgumentException("Группа не записана на курс");
        }
    }

    private void fillScheduleFromDto(Schedule schedule, ScheduleDtoRequest scheduleDtoRequest) {
        StudentGroup studentGroup = getGroupOrThrow(scheduleDtoRequest.getStudentGroupId());
        Course course = getCourseOrThrow(scheduleDtoRequest.getCourseId());

        schedule.setStudentGroup(studentGroup);
        schedule.setCourse(course);
        schedule.setLessonTime(scheduleDtoRequest.getLessonTime());
    }

    private void validateGroupTimeConflictForUpdate(Long groupId, LocalDateTime lessonTime, Long scheduleId) {

        boolean exists = scheduleRepository
                .existsByStudentGroupIdAndLessonTimeAndIdNot(groupId, lessonTime, scheduleId);

        if (exists) {
            throw new IllegalArgumentException("У группы уже есть занятие в это время");
        }
    }

    private void validateTeacherTimeConflictForUpdate(Long teacherId, LocalDateTime lessonTime, Long scheduleId) {

        boolean exists = scheduleRepository
                .existsByCourseTeacherIdAndLessonTimeAndIdNot(teacherId, lessonTime, scheduleId);

        if (exists) {
            throw new IllegalArgumentException("У преподавателя уже есть занятие в это время");
        }
    }

}