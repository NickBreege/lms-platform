package org.example.lmsplatform.dao;

import org.example.lmsplatform.model.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("""
            SELECT COUNT(s) > 0
            FROM Schedule s
            WHERE s.studentGroup.id = :groupId
              AND s.lessonStart < :lessonEnd
              AND s.lessonEnd > :lessonStart
            """)
    boolean existsGroupConflict(@Param("groupId") Long groupId,
                                @Param("lessonStart") LocalDateTime lessonStart,
                                @Param("lessonEnd") LocalDateTime lessonEnd);


    @Query("""
            SELECT COUNT(s) > 0
            FROM Schedule s
            WHERE s.course.teacher.id = :teacherId
              AND s.lessonStart < :lessonEnd
              AND s.lessonEnd > :lessonStart
            """)
    boolean existsTeacherConflict(@Param("teacherId") Long teacherId,
                                  @Param("lessonStart") LocalDateTime lessonStart,
                                  @Param("lessonEnd") LocalDateTime lessonEnd);


    @Query("""
            SELECT COUNT(s) > 0
            FROM Schedule s
            WHERE s.studentGroup.id = :groupId
              AND s.id <> :scheduleId
              AND s.lessonStart < :lessonEnd
              AND s.lessonEnd > :lessonStart
            """)
    boolean existsGroupConflictForUpdate(
            @Param("groupId") Long groupId,
            @Param("lessonStart") LocalDateTime lessonStart,
            @Param("lessonEnd") LocalDateTime lessonEnd,
            @Param("scheduleId") Long scheduleId);


    @Query("""
            SELECT COUNT(s) > 0
            FROM Schedule s
            WHERE s.course.teacher.id = :teacherId
              AND s.id <> :scheduleId
              AND s.lessonStart < :lessonEnd
              AND s.lessonEnd > :lessonStart
            """)
    boolean existsTeacherConflictForUpdate(
            @Param("teacherId") Long teacherId,
            @Param("lessonStart") LocalDateTime lessonStart,
            @Param("lessonEnd") LocalDateTime lessonEnd,
            @Param("scheduleId") Long scheduleId);


    List<Schedule> findByStudentGroupIdOrderByLessonStartAsc(Long groupId);

    List<Schedule> findByCourseTeacherIdOrderByLessonStartAsc(Long teacherId);

    void deleteByLessonStartBefore(LocalDateTime lessonStart);
}
