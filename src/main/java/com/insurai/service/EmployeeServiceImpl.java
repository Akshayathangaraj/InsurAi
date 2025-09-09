package com.insurai.insurai.service;

import com.insurai.insurai.dto.EmployeeRequestDTO;
import com.insurai.insurai.dto.EmployeeResponseDTO;
import com.insurai.insurai.exception.ResourceNotFoundException;
import com.insurai.insurai.mapper.EmployeeMapper;
import com.insurai.insurai.model.Employee;
import com.insurai.insurai.model.Insurance;
import com.insurai.insurai.repository.EmployeeRepository;
import com.insurai.insurai.repository.InsuranceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepo;
    private final InsuranceRepository insuranceRepo;

    public EmployeeServiceImpl(EmployeeRepository employeeRepo, InsuranceRepository insuranceRepo) {
        this.employeeRepo = employeeRepo;
        this.insuranceRepo = insuranceRepo;
    }

    // Helper: load policies by ID with validation
    private List<Insurance> loadPoliciesByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return Collections.emptyList();

        List<Insurance> found = insuranceRepo.findAllById(ids);
        for (Long id : ids) {
            boolean exists = found.stream().anyMatch(p -> p.getId().equals(id));
            if (!exists) {
                throw new ResourceNotFoundException("Insurance id " + id + " not found");
            }
        }
        return found;
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepo.findAll().stream()
                .map(EmployeeMapper::toEmployeeResponseDTO)
                .toList();
    }

    @Override
    public EmployeeResponseDTO getById(Long id) {
        Employee e = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee id " + id + " not found"));
        return EmployeeMapper.toEmployeeResponseDTO(e);
    }

    @Override
    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {
        List<Insurance> policies = loadPoliciesByIds(dto.getPolicies());
        Employee e = EmployeeMapper.toEmployeeEntity(dto, policies);

        // Set default values if missing
        if (e.getEmployeeStatus() == null) e.setEmployeeStatus("Active");
        if (e.getDateOfJoining() == null) e.setDateOfJoining(java.time.LocalDate.now());

        Employee saved = employeeRepo.save(e);
        return EmployeeMapper.toEmployeeResponseDTO(saved);
    }

    @Override
    @Transactional
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO dto) {
        Employee existing = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee id " + id + " not found"));

        existing.setName(dto.getName());
        existing.setDepartment(dto.getDepartment());
        existing.setRole(dto.getRole());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());
        existing.setPassword(dto.getPassword());
        existing.setAge(dto.getAge() == null ? existing.getAge() : dto.getAge());

        // Update new fields
        existing.setDateOfJoining(dto.getDateOfJoining() != null ? dto.getDateOfJoining() : existing.getDateOfJoining());
        existing.setEmployeeStatus(dto.getEmployeeStatus() != null ? dto.getEmployeeStatus() : existing.getEmployeeStatus());
        existing.setProfilePhotoURL(dto.getProfilePhotoURL() != null ? dto.getProfilePhotoURL() : existing.getProfilePhotoURL());

        existing.setPolicies(loadPoliciesByIds(dto.getPolicies()));

        Employee updated = employeeRepo.save(existing);
        return EmployeeMapper.toEmployeeResponseDTO(updated);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        Employee existing = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee id " + id + " not found"));
        employeeRepo.delete(existing);
    }
}
