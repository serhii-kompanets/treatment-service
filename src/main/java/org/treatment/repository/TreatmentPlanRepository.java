package org.treatment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.treatment.entities.TreatmentPlan;

public interface TreatmentPlanRepository extends JpaRepository<TreatmentPlan, Long> {
}
