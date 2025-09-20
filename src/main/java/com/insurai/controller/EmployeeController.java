package com.insurai.insurai.controller;

import com.insurai.insurai.dto.EmployeeRequestDTO;
import com.insurai.insurai.dto.EmployeeResponseDTO;
import com.insurai.insurai.model.Employee;
import com.insurai.insurai.model.Insurance;
import com.insurai.insurai.service.EmployeeService;
import com.insurai.insurai.service.InsuranceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final InsuranceService insuranceService;

    public EmployeeController(EmployeeService employeeService, InsuranceService insuranceService) {
        this.employeeService = employeeService;
        this.insuranceService = insuranceService;
    }

    // ✅ GET all employees
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // ✅ GET employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getById(id));
    }

    // ✅ POST create a new employee
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO dto) {
        return ResponseEntity.ok(employeeService.createEmployee(dto));
    }

    // ✅ PUT update employee
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable Long id,
                                                              @Valid @RequestBody EmployeeRequestDTO dto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, dto));
    }

    // ✅ DELETE employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Assign insurance policy to employee (robust version)
    @PostMapping("/{employeeId}/assignPolicy/{policyId}")
    @Transactional
    public ResponseEntity<String> assignPolicy(@PathVariable Long employeeId,
                                               @PathVariable Long policyId) {
        // Fetch employee entity
        Employee employee = employeeService.getEmployeeEntityById(employeeId);

        // Fetch insurance entity
        Insurance policy = insuranceService.getById(policyId);

        // Initialize policies list if null
        if (employee.getPolicies() == null) {
            employee.setPolicies(new ArrayList<>());
        }

        // Avoid duplicate assignment
        boolean alreadyAssigned = employee.getPolicies().stream()
                .anyMatch(p -> p.getId().equals(policy.getId()));

        if (!alreadyAssigned) {
            employee.getPolicies().add(policy);
            employeeService.saveEntity(employee);
            return ResponseEntity.ok("Policy assigned successfully!");
        } else {
            return ResponseEntity.badRequest().body("Policy already assigned to this employee.");
        }
    }
}
