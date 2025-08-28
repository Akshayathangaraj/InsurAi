package com.insurai.insurai.controller;

import com.insurai.insurai.model.Insurance;
import com.insurai.insurai.service.InsuranceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insurance")
@CrossOrigin(origins = "*")
public class InsuranceController {

    private final InsuranceService service;

    public InsuranceController(InsuranceService service) {
        this.service = service;
    }

    // GET all insurance policies
    @GetMapping
    public ResponseEntity<List<Insurance>> getAllPolicies() {
        List<Insurance> policies = service.getAllPolicies();
        return ResponseEntity.ok(policies);
    }

    // GET a policy by ID
    @GetMapping("/{id}")
    public ResponseEntity<Insurance> getPolicyById(@PathVariable Long id) {
        Insurance policy = service.getById(id);
        return ResponseEntity.ok(policy);
    }

    // POST create a new insurance policy
    @PostMapping
    public ResponseEntity<Insurance> createPolicy(@RequestBody Insurance insurance) {
        Insurance savedPolicy = service.savePolicy(insurance);
        return ResponseEntity.ok(savedPolicy);
    }

    // PUT – Update policy
    @PutMapping("/{id}")
    public ResponseEntity<Insurance> updatePolicy(@PathVariable Long id,
                                                  @RequestBody Insurance updates) {
        Insurance updatedPolicy = service.updatePolicy(id, updates);
        return ResponseEntity.ok(updatedPolicy);
    }

    // DELETE – Remove policy
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {
        service.deletePolicy(id);
        return ResponseEntity.noContent().build();
    }
}
