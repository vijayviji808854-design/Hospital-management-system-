package com.example.hms.controller;

import com.example.hms.model.Patient;
import com.example.hms.repo.PatientRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientRepository repo;

    public PatientController(PatientRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Patient> all() {
        return repo.findAll();
    }

    @PostMapping
    public Patient create(@Valid @RequestBody Patient p) {
        return repo.save(p);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> one(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> update(@PathVariable Long id, @Valid @RequestBody Patient p) {
        return repo.findById(id).map(ex -> {
            ex.setName(p.getName());
            ex.setDob(p.getDob());
            ex.setEmail(p.getEmail());
            ex.setPhone(p.getPhone());
            ex.setAddress(p.getAddress());
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
