package com.example.hms.controller;

import com.example.hms.model.Appointment;
import com.example.hms.model.Doctor;
import com.example.hms.model.Patient;
import com.example.hms.repo.AppointmentRepository;
import com.example.hms.repo.DoctorRepository;
import com.example.hms.repo.PatientRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentRepository appointmentRepo;
    private final DoctorRepository doctorRepo;
    private final PatientRepository patientRepo;

    public AppointmentController(AppointmentRepository appointmentRepo, DoctorRepository doctorRepo, PatientRepository patientRepo) {
        this.appointmentRepo = appointmentRepo;
        this.doctorRepo = doctorRepo;
        this.patientRepo = patientRepo;
    }

    @GetMapping
    public List<Appointment> all() {
        return appointmentRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<Appointment> create(@Valid @RequestBody Appointment a) {
        // Ensure doctor & patient exist if IDs provided
        if (a.getDoctor() != null && a.getDoctor().getId() != null) {
            Optional<Doctor> d = doctorRepo.findById(a.getDoctor().getId());
            if (d.isEmpty()) return ResponseEntity.badRequest().build();
            a.setDoctor(d.get());
        }
        if (a.getPatient() != null && a.getPatient().getId() != null) {
            Optional<Patient> p = patientRepo.findById(a.getPatient().getId());
            if (p.isEmpty()) return ResponseEntity.badRequest().build();
            a.setPatient(p.get());
        }
        return ResponseEntity.ok(appointmentRepo.save(a));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> one(@PathVariable Long id) {
        return appointmentRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> update(@PathVariable Long id, @Valid @RequestBody Appointment a) {
        return appointmentRepo.findById(id).map(ex -> {
            if (a.getDoctor() != null && a.getDoctor().getId() != null) {
                doctorRepo.findById(a.getDoctor().getId()).ifPresent(ex::setDoctor);
            }
            if (a.getPatient() != null && a.getPatient().getId() != null) {
                patientRepo.findById(a.getPatient().getId()).ifPresent(ex::setPatient);
            }
            if (a.getStartTime() != null) ex.setStartTime(a.getStartTime());
            if (a.getEndTime() != null) ex.setEndTime(a.getEndTime());
            if (a.getReason() != null) ex.setReason(a.getReason());
            if (a.getStatus() != null) ex.setStatus(a.getStatus());
            return ResponseEntity.ok(appointmentRepo.save(ex));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (appointmentRepo.existsById(id)) {
            appointmentRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
