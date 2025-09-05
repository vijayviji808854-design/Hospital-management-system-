package com.example.hms.controller;

import com.example.hms.model.Doctor;
import com.example.hms.repo.DoctorRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorRepository repo;

    public DoctorController(DoctorRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Doctor> all() {
        return repo.findAll();
    }

    @PostMapping
    public Doctor create(@Valid @RequestBody Doctor d) {
        return repo.save(d);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> one(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> update(@PathVariable Long id, @Valid @RequestBody Doctor d) {
        return repo.findById(id).map(ex -> {
            ex.setName(d.getName());
            ex.setSpecialization(d.getSpecialization());
            ex.setEmail(d.getEmail());
            ex.setPhone(d.getPhone());
            return ResponseEntity.ok(repo.save(ex));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
