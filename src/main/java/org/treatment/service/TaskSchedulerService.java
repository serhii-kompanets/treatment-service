package org.treatment.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class TaskSchedulerService {
    private final TaskManager taskManager;
    private final ScheduledExecutorService scheduler;

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskSchedulerService.class);

    public TaskSchedulerService(TaskManager taskManager) {
        this.taskManager = taskManager;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    @PostConstruct
    public void startScheduler() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                taskManager.manageTasks();
            } catch (Exception e) {
                LOGGER.warn("Error executing task manager: {}", e.getMessage());
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    @PreDestroy
    public void stopScheduler() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }
}
