package org.treatment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.treatment.entities.TreatmentPlan;
import org.treatment.entities.TreatmentTask;
import org.treatment.repository.TreatmentPlanRepository;
import org.treatment.repository.TreatmentTaskRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskManager {
    private static final Logger logger = LoggerFactory.getLogger(TaskManager.class);

    private final TreatmentPlanRepository planRepository;
    private final TreatmentTaskRepository taskRepository;

    @Autowired
    public TaskManager(TreatmentPlanRepository planRepository, TreatmentTaskRepository taskRepository) {
        this.planRepository = planRepository;
        this.taskRepository = taskRepository;
    }

    public void manageTasks() {
        logger.info("Starting task management...");
        List<TreatmentPlan> plans = planRepository.findAll();
        plans.forEach(this::processTreatmentPlan);
        logger.info("Task management completed.");
    }

    private void processTreatmentPlan(TreatmentPlan plan) {
        logger.info("Processing treatment plan for patient {} with recurrence pattern: {}",
                plan.getPatientId(), plan.getRecurrencePattern());

        LocalDateTime nextOccurrence = plan.getStartTime();

        while (nextOccurrence != null && (plan.getEndTime() == null || !nextOccurrence.isAfter(plan.getEndTime()))) {
            if (!taskRepository.existsByPatientIdAndStartTime(plan.getPatientId(), nextOccurrence)) {
                createTask(plan, nextOccurrence);
            }
            logger.info("Next occurrence for plan {} is at {}", plan.getPatientId(), nextOccurrence);

            nextOccurrence = getNextOccurrence(plan.getRecurrencePattern(), nextOccurrence);
        }
    }

    private void createTask(TreatmentPlan plan, LocalDateTime startTime) {
        TreatmentTask task = new TreatmentTask();
        task.setTreatmentAction(plan.getTreatmentAction());
        task.setPatientId(plan.getPatientId());
        task.setStartTime(startTime);
        task.setStatus("Active");
        taskRepository.save(task);
        logger.info("Created new task for patient {} at {}", plan.getPatientId(), startTime);
    }

    private void updateTask(TreatmentTask task) {
        task.setStatus("Updated");
        taskRepository.save(task);
    }

    public LocalDateTime getNextOccurrence(String recurrencePattern, LocalDateTime lastOccurrence) {
        return switch (recurrencePattern.toLowerCase()) {
            case "daily" -> lastOccurrence.plusDays(1);
            case "hourly" -> lastOccurrence.plusHours(1);
            case "weekly" -> lastOccurrence.plusWeeks(1);
            case "every_30_minutes" -> lastOccurrence.plusMinutes(30);
            default -> throw new IllegalArgumentException("Unknown recurrence pattern: " + recurrencePattern);
        };
    }
}
