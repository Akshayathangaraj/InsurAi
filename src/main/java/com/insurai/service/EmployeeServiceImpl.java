package com.insurai.insurai.service;

import com.insurai.insurai.dto.EmployeeRequestDTO;
import com.insurai.insurai.dto.EmployeeResponseDTO;
import com.insurai.insurai.dto.PolicyDTO;
import com.insurai.insurai.exception.ResourceNotFoundException;
import com.insurai.insurai.model.Employee;
import com.insurai.insurai.model.Insurance;
import com.insurai.insurai.repository.EmployeeRepository;
import com.insurai.insurai.repository.InsuranceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepo;
    private final InsuranceRepository insuranceRepo;

    public EmployeeServiceImpl(EmployeeRepository employeeRepo, InsuranceRepository insuranceRepo) {
        this.employeeRepo = employeeRepo;
        this.insuranceRepo = insuranceRepo;
    }

    // helpers: map policy entity to DTO
    private PolicyDTO mapToPolicyDTO(Insurance p) {
        return new PolicyDTO(p.getId(), p.getPolicyName(), p.getHolderName(), p.getPremium());
    }

    private EmployeeResponseDTO mapToEmployeeResponse(Employee e) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setDepartment(e.getDepartment());
        dto.setEmail(e.getEmail());
        dto.setAge(e.getAge());
        // guard null policies
        List<PolicyDTO> policies = Optional.ofNullable(e.getPolicies())
                .orElse(Collections.emptyList())
                .stream()
                .map(this::mapToPolicyDTO)
                .collect(Collectors.toList());
        dto.setPolicies(policies);
        return dto;
    }

    private List<Insurance> loadPoliciesByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return Collections.emptyList();
        List<Insurance> found = insuranceRepo.findAllById(ids);
        Set<Long> foundIds = found.stream()
                .map(Insurance::getId)
                .collect(Collectors.toSet());
        for (Long id : ids) {
            if (!foundIds.contains(id)) {
                throw new ResourceNotFoundException("Insurance id " + id + " not found");
            }
        }
        return found;
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepo.findAll().stream()
                .map(this::mapToEmployeeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponseDTO getById(Long id) {
        Employee e = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee id " + id + " not found"));
        return mapToEmployeeResponse(e);
    }

    @Override
    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {
        List<Insurance> policies = loadPoliciesByIds(dto.getPolicyIds());

        Employee e = new Employee();
        e.setName(dto.getName());
        e.setDepartment(dto.getDepartment());
        e.setEmail(dto.getEmail());
        e.setAge(dto.getAge() == null ? 0 : dto.getAge());
        e.setPolicies(policies);

        Employee saved = employeeRepo.save(e);
        return mapToEmployeeResponse(saved);
    }

    @Override
    @Transactional
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO dto) {
        Employee existing = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee id " + id + " not found"));

        existing.setName(dto.getName());
        existing.setDepartment(dto.getDepartment());
        existing.setEmail(dto.getEmail());
        existing.setAge(dto.getAge() == null ? existing.getAge() : dto.getAge());

        List<Insurance> policies = loadPoliciesByIds(dto.getPolicyIds());
        existing.setPolicies(policies);

        Employee updated = employeeRepo.save(existing);
        return mapToEmployeeResponse(updated);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        Employee existing = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee id " + id + " not found"));
        employeeRepo.delete(existing);
    }
}
