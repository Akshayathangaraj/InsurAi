package com.insurai.insurai.controller;

import com.insurai.insurai.dto.PolicyDTO;
import com.insurai.insurai.model.Insurance;
import com.insurai.insurai.service.InsuranceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/insurance")
@CrossOrigin(origins = "*")
public class InsuranceController {

    private final InsuranceService service;

    public InsuranceController(InsuranceService service) {
        this.service = service;
    }

    // Convert entity to DTO to avoid circular references
    private PolicyDTO mapToDTO(Insurance insurance) {
    return new PolicyDTO(
        insurance.getId(),
        insurance.getPolicyName(),
        insurance.getPolicyType(),
        insurance.getPremium(),
        insurance.getCoverageAmount(),
        insurance.getStartDate(),
        insurance.getEndDate(),
        insurance.getStatus(),
        insurance.getRenewalDate(),
        insurance.getClaimLimit(),
        insurance.getProvider(),
        insurance.getRiskLevel(),
        insurance.getGracePeriod(),
        insurance.getTermsAndConditions()
    );
}


    // GET all insurance policies
    @GetMapping
    public ResponseEntity<List<PolicyDTO>> getAllPolicies() {
        List<PolicyDTO> policies = service.getAllPolicies().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(policies);
    }

    // GET a policy by ID
    @GetMapping("/{id}")
    public ResponseEntity<PolicyDTO> getPolicyById(@PathVariable Long id) {
        Insurance policy = service.getById(id);
        return ResponseEntity.ok(mapToDTO(policy));
    }

    // POST create a new insurance policy
    @PostMapping
    public ResponseEntity<PolicyDTO> createPolicy(@RequestBody Insurance insurance) {
        Insurance savedPolicy = service.savePolicy(insurance);
        return ResponseEntity.ok(mapToDTO(savedPolicy));
    }

    // PUT – Update policy
    @PutMapping("/{id}")
    public ResponseEntity<PolicyDTO> updatePolicy(@PathVariable Long id,
                                                  @RequestBody Insurance updates) {
        Insurance updatedPolicy = service.updatePolicy(id, updates);
        return ResponseEntity.ok(mapToDTO(updatedPolicy));
    }

    // DELETE – Remove policy
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {
        service.deletePolicy(id);
        return ResponseEntity.noContent().build();
    }
}
