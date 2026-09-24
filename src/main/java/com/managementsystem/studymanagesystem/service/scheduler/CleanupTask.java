package com.managementsystem.studymanagesystem.service.scheduler;

import com.managementsystem.studymanagesystem.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CleanupTask {

    private final ScheduleService scheduleService;

    @Scheduled(cron = "0 0 3 * * *")
    public void removeOldSchedules() {
        scheduleService.deleteOlderThan(LocalDateTime.now().minusYears(1));
    }
}