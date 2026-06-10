# Smart Clinic System - Database Schema Design

This document outlines the hybrid database architecture for the Smart Clinic System. It leverages **MySQL** for structured, highly transactional data requiring ACID compliance, and **MongoDB** for flexible, hierarchical data structures.

---

## 1. MySQL Relational Database Design

The relational schema ensures strict data integrity, handles complex relationships, and prevents scheduling conflicts.

```sql
CREATE DATABASE IF NOT EXISTS smart_clinic_db;
USE smart_clinic_db;

-- 1. ADM_USERS TABLE (System Administrators)
CREATE TABLE adm_users (
    admin_id INT AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_adm_users PRIMARY KEY (admin_id),
    CONSTRAINT uq_adm_email UNIQUE (email),
    CONSTRAINT uq_adm_username UNIQUE (username)
);

-- 2. DOCTORS TABLE
CREATE TABLE doctors (
    doctor_id INT AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    specialization VARCHAR(100) NOT NULL,
    license_number VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    CONSTRAINT pk_doctors PRIMARY KEY (doctor_id),
    CONSTRAINT uq_doc_license UNIQUE (license_number),
    CONSTRAINT uq_doc_email UNIQUE (email)
);

-- 3. PATIENTS TABLE
CREATE TABLE patients (
    patient_id INT AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender ENUM('Male', 'Female', 'Other') NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    email VARCHAR(100),
    emergency_contact_name VARCHAR(100),
    emergency_contact_phone VARCHAR(15),
    CONSTRAINT pk_patients PRIMARY KEY (patient_id),
    CONSTRAINT uq_pat_email UNIQUE (email)
);

-- 4. APPOINTMENTS TABLE
CREATE TABLE appointments (
    appointment_id INT AUTO_INCREMENT,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    appointment_date_time DATETIME NOT NULL,
    status ENUM('Scheduled', 'Completed', 'Cancelled', 'No-Show') DEFAULT 'Scheduled',
    reason_for_visit TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_appointments PRIMARY KEY (appointment_id),
    CONSTRAINT fk_app_patient FOREIGN KEY (patient_id) 
        REFERENCES patients(patient_id) ON DELETE RESTRICT,
    CONSTRAINT fk_app_doctor FOREIGN KEY (doctor_id) 
        REFERENCES doctors(doctor_id) ON DELETE RESTRICT,
    -- Constraint to prevent booking the same doctor for the exact same slot
    CONSTRAINT uq_doctor_slot UNIQUE (doctor_id, appointment_date_time)
);
```

### Design Justifications (MySQL)
* **`ON DELETE RESTRICT`**: Prevents accidental deletion of a patient or doctor profile if they have existing billing or appointment histories.
* **`uq_doctor_slot`**: A composite unique constraint that serves as a first line of defense against double-booking errors at the database level.
* **`ENUM` Types**: Restricts data entry for statuses and genders to maintain standardized analytical data.

---

## 2. MongoDB NoSQL Collection Design

Medical prescriptions are highly variable. Different treatments require entirely different datasets (e.g., varying dosages, frequency, durations, refills, or specific lab instructions). MongoDB allows us to store these as rich, polymorphic documents without dealing with complex SQL join tables.

### Collection: `prescriptions`

#### JSON Document Example
```json
{
  "_id": {"\$oid": "65fc2a3b8d4f2c001a5b8c9d"},
  "appointment_id": 1402,
  "patient_id": 45,
  "doctor_id": 12,
  "issued_date": "2026-03-25T10:30:00Z",
  "diagnosis": "Acute Strep Throat & Mild Dehydration",
  "medications": [
    {
      "drug_name": "Amoxicillin",
      "dosage": "500mg",
      "frequency": "Three times daily",
      "duration_days": 10,
      "refills_allowed": 0,
      "instructions": "Take with food. Complete the full course."
    },
    {
      "drug_name": "Acetaminophen",
      "dosage": "325mg",
      "frequency": "Every 4-6 hours as needed",
      "duration_days": 5,
      "refills_allowed": 1,
      "instructions": "Do not exceed 4000mg in 24 hours."
    }
  ],
  "lab_tests_ordered": [
    "Throat Culture Follow-up"
  ],
  "vitals_captured": {
    "blood_pressure": "120/80",
    "heart_rate_bpm": 76,
    "temperature_c": 38.2
  },
  "digital_signature_id": "SIG-DOC12-9923"
}
```

### Design Justifications (MongoDB)
* **Hierarchical Arrays**: The `medications` array nests multiple drugs with unique instructions inside a single document, keeping the entire medical encounter atomic and easy to retrieve.
* **Flexible Schema**: If a patient does not need `lab_tests_ordered` or `vitals_captured`, those fields can be completely omitted from the document without wasting storage space.
* **Reference Keys**: Storing standard numerical `patient_id` and `doctor_id` fields allows the application layer to bridge data effectively between MySQL and MongoDB.
