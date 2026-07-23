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
        validateLessonInterval(scheduleDtoRequest.getLessonStart(), scheduleDtoRequest.getLessonEnd());

        StudentGroup group = getGroupOrThrow(scheduleDtoRequest.getStudentGroupId());
        Course course = getCourseOrThrow(scheduleDtoRequest.getCourseId());

        validateGroupAssignedToCourse(group, course);

        validateGroupTimeConflict(group.getId(),
                scheduleDtoRequest.getLessonStart(),
                scheduleDtoRequest.getLessonEnd());

        validateTeacherTimeConflict(course.getTeacher().getId(),
                scheduleDtoRequest.getLessonStart(),
                scheduleDtoRequest.getLessonEnd());

        Schedule schedule = new Schedule();

        fillScheduleFromDto(schedule, group, course, scheduleDtoRequest);

        return scheduleMapper.toDtoResponse(scheduleRepository.save(schedule));
    }

    @Transactional(readOnly = true)
    public List<ScheduleDtoResponse> getGroupSchedule(Long groupId) {
        StudentGroup group = getGroupOrThrow(groupId);

        return scheduleRepository.findByStudentGroupIdOrderByLessonStartAsc(group.getId())
                .stream()
                .map(scheduleMapper::toDtoResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ScheduleDtoResponse> getTeacherSchedule(Long teacherId) {
        Teacher teacher = getTeacherOrThrow(teacherId);

        return scheduleRepository.findByCourseTeacherIdOrderByLessonStartAsc(teacher.getId())
                .stream()
                .map(scheduleMapper::toDtoResponse)
                .toList();
    }

    @Transactional
    public ScheduleDtoResponse updateSchedule(Long scheduleId, ScheduleDtoRequest scheduleDtoRequest) {
        validateLessonInterval(scheduleDtoRequest.getLessonStart(), scheduleDtoRequest.getLessonEnd());

        Schedule schedule = getScheduleOrThrow(scheduleId);

        StudentGroup group = getGroupOrThrow(scheduleDtoRequest.getStudentGroupId());
        Course course = getCourseOrThrow(scheduleDtoRequest.getCourseId());

        validateGroupAssignedToCourse(group, course);

        validateGroupTimeConflictForUpdate(group.getId(), scheduleDtoRequest.getLessonStart(),
                scheduleDtoRequest.getLessonEnd(), schedule.getId());

        validateTeacherTimeConflictForUpdate(
                course.getTeacher().getId(),
                scheduleDtoRequest.getLessonStart(),
                scheduleDtoRequest.getLessonEnd(),
                schedule.getId());

        fillScheduleFromDto(schedule, group, course, scheduleDtoRequest);

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

    private StudentGroup getGroupOrThrow(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));
    }

    private Schedule getScheduleOrThrow(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleNotFoundException(scheduleId));
    }

    private void validateLessonInterval(LocalDateTime lessonStart, LocalDateTime lessonEnd) {
        if (!lessonEnd.isAfter(lessonStart)) {
            throw new IllegalArgumentException("Время окончания занятия должно быть позже времени начала");
        }
    }

    private void validateGroupTimeConflict(Long groupId, LocalDateTime lessonStart, LocalDateTime lessonEnd) {
        boolean exists = scheduleRepository.existsGroupConflict(groupId, lessonStart, lessonEnd);

        if (exists) {
            throw new IllegalArgumentException("У группы уже есть занятие в это время");
        }
    }

    private void validateTeacherTimeConflict(Long teacherId, LocalDateTime lessonStart, LocalDateTime lessonEnd) {
        boolean exists = scheduleRepository.existsTeacherConflict(teacherId, lessonStart, lessonEnd);

        if (exists) {
            throw new IllegalArgumentException("У преподавателя уже есть занятие в это время");
        }
    }

    private void validateGroupAssignedToCourse(StudentGroup group, Course course) {
        if (!group.getCourses().contains(course)) {
            throw new IllegalArgumentException("Группа не записана на этот курс");
        }
    }

    private void fillScheduleFromDto(Schedule schedule,
                                     StudentGroup studentGroup,
                                     Course course,
                                     ScheduleDtoRequest scheduleDtoRequest) {

        schedule.setStudentGroup(studentGroup);
        schedule.setCourse(course);
        schedule.setLessonStart(scheduleDtoRequest.getLessonStart());
        schedule.setLessonEnd(scheduleDtoRequest.getLessonEnd());
    }

    private void validateGroupTimeConflictForUpdate(Long groupId,
                                                    LocalDateTime lessonStart,
                                                    LocalDateTime lessonEnd,
                                                    Long scheduleId) {

        boolean exists = scheduleRepository.existsGroupConflictForUpdate(groupId, lessonStart, lessonEnd, scheduleId);

        if (exists) {
            throw new IllegalArgumentException("У группы уже есть занятие в это время");
        }
    }

    private void validateTeacherTimeConflictForUpdate(Long teacherId,
                                                      LocalDateTime lessonStart,
                                                      LocalDateTime lessonEnd,
                                                      Long scheduleId) {

        boolean exists = scheduleRepository
                .existsTeacherConflictForUpdate(teacherId, lessonStart, lessonEnd, scheduleId);

        if (exists) {
            throw new IllegalArgumentException("У преподавателя уже есть занятие в это время");
        }
    }

}