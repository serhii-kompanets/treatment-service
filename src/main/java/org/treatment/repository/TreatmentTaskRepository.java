package org.treatment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.treatment.entities.TreatmentTask;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TreatmentTaskRepository extends JpaRepository<TreatmentTask, Long> {
    Optional<TreatmentTask> findByPatientIdAndStartTime(String patientId, LocalDateTime startTime);
    boolean existsByPatientIdAndStartTime(String patientId, LocalDateTime startTime);
}
