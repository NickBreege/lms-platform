package org.example.lms_platform.dao;

import org.example.lms_platform.model.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByGroupId(Long groupId);

    List<Schedule> findByCourseTeacherId(Long teacherId);
}
