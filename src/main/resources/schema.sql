CREATE TABLE treatment_plans (
                                 id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                 treatment_action VARCHAR(50) NOT NULL,
                                 patient_id VARCHAR(50) NOT NULL,
                                 start_time TIMESTAMP NOT NULL,
                                 end_time TIMESTAMP,
                                 recurrence_pattern VARCHAR(50) NOT NULL
);

CREATE TABLE treatment_tasks (
                                 id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                 treatment_action VARCHAR(50) NOT NULL,
                                 patient_id VARCHAR(50) NOT NULL,
                                 start_time TIMESTAMP NOT NULL,
                                 status VARCHAR(20) NOT NULL
);