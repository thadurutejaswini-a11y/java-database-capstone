USE smart_clinic_db;

-- 1. Insert Sample Data
INSERT INTO doctors (first_name, last_name, specialization, license_number, email, phone_number) VALUES
('Alice', 'Smith', 'Cardiology', 'LIC12345', 'alice.smith@clinic.com', '555-0101'),
('Bob', 'Johnson', 'Pediatrics', 'LIC67890', 'bob.johnson@clinic.com', '555-0102'),
('Charlie', 'Brown', 'Dermatology', 'LIC11223', 'charlie.brown@clinic.com', '555-0103');

INSERT INTO patients (first_name, last_name, date_of_birth, gender, phone_number, email) VALUES
('John', 'Doe', '1985-05-12', 'Male', '555-1001', 'john.doe@email.com'),
('Jane', 'Roe', '1990-08-23', 'Female', '555-1002', 'jane.roe@email.com'),
('Jim', 'Beam', '1978-11-04', 'Other', '555-1003', 'jim.beam@email.com'),
('Mary', 'Lamb', '2015-03-30', 'Female', '555-1004', 'mary.lamb@email.com');

INSERT INTO appointments (patient_id, doctor_id, appointment_date_time, status, reason_for_visit) VALUES
(1, 1, '2026-03-25 09:00:00', 'Completed', 'Routine Checkup'),
(2, 1, '2026-03-25 10:30:00', 'Scheduled', 'Follow-up'),
(3, 2, '2026-03-25 11:00:00', 'Scheduled', 'Mild Fever'),
(4, 1, '2026-03-10 14:00:00', 'Completed', 'Chest Pain'),
(1, 2, '2026-03-12 15:00:00', 'Completed', 'Vaccination'),
(2, 2, '2026-04-05 09:30:00', 'Scheduled', 'Allergy Check'),
(3, 2, '2026-04-06 10:00:00', 'Scheduled', 'Skin Rash');

-- 2. Define Stored Procedures
DELIMITER //

CREATE PROCEDURE GetDailyAppointmentReportByDoctor(IN report_date DATE)
BEGIN
    SELECT d.doctor_id, CONCAT(d.first_name, ' ', d.last_name) AS doctor_name, d.specialization, COUNT(a.appointment_id) AS total_appointments
    FROM doctors d LEFT JOIN appointments a ON d.doctor_id = a.doctor_id AND DATE(a.appointment_date_time) = report_date
    WHERE d.is_active = TRUE GROUP BY d.doctor_id, d.first_name, d.last_name, d.specialization ORDER BY total_appointments DESC;
END //

CREATE PROCEDURE GetDoctorWithMostPatientsByMonth(IN report_year INT, IN report_month INT)
BEGIN
    SELECT d.doctor_id, CONCAT(d.first_name, ' ', d.last_name) AS doctor_name, COUNT(a.appointment_id) AS patients_seen
    FROM doctors d JOIN appointments a ON d.doctor_id = a.doctor_id
    WHERE YEAR(a.appointment_date_time) = report_year AND MONTH(a.appointment_date_time) = report_month AND a.status IN ('Scheduled', 'Completed')
    GROUP BY d.doctor_id, d.first_name, d.last_name ORDER BY patients_seen DESC LIMIT 1;
END //

CREATE PROCEDURE GetDoctorWithMostPatientsByYear(IN report_year INT)
BEGIN
    SELECT d.doctor_id, CONCAT(d.first_name, ' ', d.last_name) AS doctor_name, COUNT(a.appointment_id) AS patients_seen
    FROM doctors d JOIN appointments a ON d.doctor_id = a.doctor_id
    WHERE YEAR(a.appointment_date_time) = report_year AND a.status IN ('Scheduled', 'Completed')
    GROUP BY d.doctor_id, d.first_name, d.last_name ORDER BY patients_seen DESC LIMIT 1;
END //

DELIMITER ;
