package org.example.lmsplatform.scheduler;

import lombok.RequiredArgsConstructor;
import org.example.lmsplatform.dao.ScheduleRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ScheduleCleanupTask {
    private final ScheduleRepository scheduleRepository;


    @Scheduled(cron = "${app.cleanup.cron}")
    @Transactional
    public void deleteOldSchedules() {
        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);
        scheduleRepository.deleteByLessonStartBefore(oneYearAgo);
    }
}
