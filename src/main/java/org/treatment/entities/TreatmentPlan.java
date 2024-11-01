package org.treatment.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "treatment_plans")
public class TreatmentPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String treatmentAction;
    private String patientId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String recurrencePattern;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTreatmentAction() {
        return treatmentAction;
    }

    public void setTreatmentAction(String treatmentAction) {
        this.treatmentAction = treatmentAction;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getRecurrencePattern() {
        return recurrencePattern;
    }

    public void setRecurrencePattern(String recurrencePattern) {
        this.recurrencePattern = recurrencePattern;
    }
}
