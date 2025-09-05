-- Hospital Management System Database

CREATE DATABASE IF NOT EXISTS hospitaldb;
USE hospitaldb;

-- Patients table
CREATE TABLE IF NOT EXISTS patients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10),
    address VARCHAR(255)
);

-- Doctors table
CREATE TABLE IF NOT EXISTS doctors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100) NOT NULL
);

-- Appointments table
CREATE TABLE IF NOT EXISTS appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    doctor_id INT,
    appointment_date DATE,
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);

-- Sample data insertion
INSERT INTO patients (name, age, gender, address) VALUES
('John Doe', 30, 'Male', '123 Main St'),
('Jane Smith', 25, 'Female', '456 Park Ave'),
('Alice Johnson', 40, 'Female', '789 Oak Dr');

INSERT INTO doctors (name, specialization) VALUES
('Dr. Brown', 'Cardiologist'),
('Dr. Green', 'Dermatologist'),
('Dr. White', 'Neurologist');

INSERT INTO appointments (patient_id, doctor_id, appointment_date) VALUES
(1, 1, '2025-09-10'),
(2, 2, '2025-09-12'),
(3, 3, '2025-09-15');
