# Stored Procedure Execution Outputs

## Deliverable 1: Daily Appointment Report
* **SQL Statement**: `CALL GetDailyAppointmentReportByDoctor('2026-03-25');`
```text
+-----------+---------------+----------------+--------------------+

| doctor_id | doctor_name   | specialization | total_appointments |
+-----------+---------------+----------------+--------------------+

|         1 | Alice Smith   | Cardiology     |                  2 |
|         2 | Bob Johnson   | Pediatrics     |                  1 |
|         3 | Charlie Brown | Dermatology    |                  0 |
+-----------+---------------+----------------+--------------------+
```

## Deliverable 2: Top Doctor by Specific Month
* **SQL Statement**: `CALL GetDoctorWithMostPatientsByMonth(2026, 3);`
```text
+-----------+-------------+---------------+

| doctor_id | doctor_name | patients_seen |
+-----------+-------------+---------------+

|         1 | Alice Smith |             3 |
+-----------+-------------+---------------+
```

## Deliverable 3: Top Doctor by Given Year
* **SQL Statement**: `CALL GetDoctorWithMostPatientsByYear(2026);`
```text
+-----------+-------------+---------------+

| doctor_id | doctor_name | patients_seen |
+-----------+-------------+---------------+

|         2 | Bob Johnson |             4 |
+-----------+-------------+---------------+
```
