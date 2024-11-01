INSERT INTO treatment_plans (treatment_action, patient_id, start_time, end_time, recurrence_pattern)
VALUES ('ActionA', 'Patient001', '2024-01-01 08:00:00', '2024-01-10 08:00:00', 'daily');

INSERT INTO treatment_tasks (treatment_action, patient_id, start_time, status)
VALUES ('ActionA', 'Patient001', '2024-01-01 08:00:00', 'Active');