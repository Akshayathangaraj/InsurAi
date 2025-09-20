package com.insurai.insurai.service;

import com.insurai.insurai.dto.EmployeeRequestDTO;
import com.insurai.insurai.dto.EmployeeResponseDTO;
import com.insurai.insurai.model.Employee; // ✅ added import

import java.util.List;

public interface EmployeeService {
    List<EmployeeResponseDTO> getAllEmployees();
    EmployeeResponseDTO getById(Long id);
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto);
    EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO dto);
    void deleteEmployee(Long id);
    boolean existsByPolicyId(Long policyId);
    // ⚡ Needed only for internal entity operations (like assigning policies)
    Employee getEmployeeEntityById(Long id);
    Employee saveEntity(Employee employee);

    // ✅ profile-only updates (photo, address, etc.)
    EmployeeResponseDTO updateProfile(Long id, EmployeeRequestDTO dto);
}
