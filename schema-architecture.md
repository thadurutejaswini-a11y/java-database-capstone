# Schema Architecture

## Overview

The Smart Clinic Management System uses a relational database schema to manage patients, doctors, appointments, prescriptions, and administrative operations.

## Database: CMS

### Patient Table

| Column Name | Data Type | Description |
|-------------|-----------|-------------|
| patient_id | INT | Primary Key |
| first_name | VARCHAR(50) | Patient First Name |
| last_name | VARCHAR(50) | Patient Last Name |
| email | VARCHAR(100) | Patient Email |
| phone | VARCHAR(15) | Contact Number |
| address | VARCHAR(255) | Patient Address |

### Doctor Table

| Column Name | Data Type | Description |
|-------------|-----------|-------------|
| doctor_id | INT | Primary Key |
| first_name | VARCHAR(50) | Doctor First Name |
| last_name | VARCHAR(50) | Doctor Last Name |
| specialization | VARCHAR(100) | Medical Specialty |
| email | VARCHAR(100) | Doctor Email |
| phone | VARCHAR(15) | Contact Number |

### Appointment Table

| Column Name | Data Type | Description |
|-------------|-----------|-------------|
| appointment_id | INT | Primary Key |
| patient_id | INT | Foreign Key |
| doctor_id | INT | Foreign Key |
| appointment_date | DATE | Appointment Date |
| appointment_time | TIME | Appointment Time |
| status | VARCHAR(20) | Appointment Status |

### Prescription Table

| Column Name | Data Type | Description |
|-------------|-----------|-------------|
| prescription_id | INT | Primary Key |
| appointment_id | INT | Foreign Key |
| medicine_name | VARCHAR(100) | Medicine Name |
| dosage | VARCHAR(50) | Dosage Details |
| instructions | TEXT | Usage Instructions |

## Entity Relationships

- One Patient can have many Appointments.
- One Doctor can have many Appointments.
- One Appointment belongs to one Patient.
- One Appointment belongs to one Doctor.
- One Appointment can have multiple Prescriptions.

## Architecture Flow

```text
Admin
  |
  +--> Manage Patients
  |
  +--> Manage Doctors
  |
  +--> Manage Appointments

Patient -----> Appointment <----- Doctor
                    |
                    |
              Prescription
```

## Database Relationships

- Patient (1) -> (M) Appointment
- Doctor (1) -> (M) Appointment
- Appointment (1) -> (M) Prescription

## Conclusion

The schema is designed to ensure efficient clinic management, maintain data integrity, and support scalable healthcare operations.
