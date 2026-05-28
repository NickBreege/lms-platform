package org.example.lmsplatform.dao;

import org.example.lmsplatform.model.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    boolean existsByStudentGroupIdAndLessonTime(Long groupId, LocalDateTime lessonTime);

    boolean existsByCourseTeacherIdAndLessonTime(Long teacherId, LocalDateTime lessonTime);

    List<Schedule> findByStudentGroupId(Long groupId);

    List<Schedule> findByCourseTeacherIdOrderByLessonTimeAsc(Long teacherId);

    boolean existsByStudentGroupIdAndLessonTimeAndIdNot(Long groupId, LocalDateTime lessonTime, Long scheduleId);

    boolean existsByCourseTeacherIdAndLessonTimeAndIdNot(Long teacherId, LocalDateTime lessonTime, Long scheduleId);
}
