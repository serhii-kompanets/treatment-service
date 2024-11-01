package org.treatment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.treatment.entities.TreatmentPlan;
import org.treatment.entities.TreatmentTask;
import org.treatment.repository.TreatmentPlanRepository;
import org.treatment.repository.TreatmentTaskRepository;
import org.treatment.service.TaskManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class TaskManagerTest {
    @Autowired
    private TaskManager taskManager;

    @Autowired
    private TreatmentTaskRepository treatmentTaskRepository;

    @Autowired
    private TreatmentPlanRepository treatmentPlanRepository;

    @BeforeEach
    public void setUp() {
        treatmentTaskRepository.deleteAll();
        treatmentPlanRepository.deleteAll();

        TreatmentPlan plan = new TreatmentPlan();
        plan.setTreatmentAction("ActionA");
        plan.setPatientId("Patient001");
        plan.setStartTime(LocalDateTime.of(2024, 1, 1, 8, 0));
        plan.setEndTime(LocalDateTime.of(2024, 1, 3, 8, 0));
        plan.setRecurrencePattern("daily");
        treatmentPlanRepository.save(plan);
    }

    @Test
    void testManageTasks_createsTasks() {
        taskManager.manageTasks();

        List<TreatmentTask> tasks = treatmentTaskRepository.findAll();
        assertThat(tasks).hasSize(3);

        tasks.forEach(task -> {
            assertThat(task.getTreatmentAction()).isEqualTo("ActionA");
            assertThat(task.getPatientId()).isEqualTo("Patient001");
            assertThat(task.getStatus()).isEqualTo("Active");
        });
    }
}
