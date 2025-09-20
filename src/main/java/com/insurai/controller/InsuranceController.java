package com.insurai.insurai.controller;

import com.insurai.insurai.dto.PolicyDTO;
import com.insurai.insurai.model.Insurance;
import com.insurai.insurai.service.EmployeeService;
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
    private final EmployeeService employeeService; // ✅ Inject EmployeeService

    public InsuranceController(InsuranceService service, EmployeeService employeeService) {
        this.service = service;
        this.employeeService = employeeService;
    }

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

    @GetMapping
    public ResponseEntity<List<PolicyDTO>> getAllPolicies() {
        List<PolicyDTO> policies = service.getAllPolicies().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(policies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolicyDTO> getPolicyById(@PathVariable Long id) {
        Insurance policy = service.getById(id);
        return ResponseEntity.ok(mapToDTO(policy));
    }

    @PostMapping
    public ResponseEntity<PolicyDTO> createPolicy(@RequestBody Insurance insurance) {
        Insurance savedPolicy = service.savePolicy(insurance);
        return ResponseEntity.ok(mapToDTO(savedPolicy));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PolicyDTO> updatePolicy(@PathVariable Long id,
                                                  @RequestBody Insurance updates) {
        Insurance updatedPolicy = service.updatePolicy(id, updates);
        return ResponseEntity.ok(mapToDTO(updatedPolicy));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePolicy(@PathVariable Long id) {
        // ✅ Use service to check if policy is assigned
        boolean isAssigned = employeeService.existsByPolicyId(id);
        if (isAssigned) {
            return ResponseEntity.badRequest()
                    .body("Cannot delete policy. It is assigned to one or more employees.");
        }

        service.deletePolicy(id);
        return ResponseEntity.ok("Policy deleted successfully!");
    }
}
