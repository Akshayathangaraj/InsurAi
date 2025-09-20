package com.insurai.insurai.mapper;

import com.insurai.insurai.dto.EmployeeRequestDTO;
import com.insurai.insurai.dto.EmployeeResponseDTO;
import com.insurai.insurai.dto.PolicyDTO;
import com.insurai.insurai.model.Employee;
import com.insurai.insurai.model.Insurance;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper {

    public static EmployeeResponseDTO toEmployeeResponseDTO(Employee employee) {
    EmployeeResponseDTO dto = new EmployeeResponseDTO();
    dto.setId(employee.getId());
    dto.setName(employee.getName());
    dto.setDepartment(employee.getDepartment());
    dto.setRole(employee.getRole());
    dto.setEmail(employee.getEmail());
    dto.setPhone(employee.getPhone());
    dto.setAge(employee.getAge());
    dto.setDateOfJoining(employee.getDateOfJoining());
    dto.setEmployeeStatus(employee.getEmployeeStatus());
    dto.setLastLogin(employee.getLastLogin());
    dto.setProfilePhotoUrl(employee.getProfilePhotoUrl()); // fixed
    dto.setAddress(employee.getAddress()); // fixed

    if (employee.getPolicies() != null) {
        List<PolicyDTO> policies = employee.getPolicies().stream()
                .map(EmployeeMapper::toPolicyDTO)
                .collect(Collectors.toList());
        dto.setPolicies(policies);
    }
    return dto;
}

public static Employee toEmployeeEntity(EmployeeRequestDTO dto, List<Insurance> policies) {
    Employee emp = new Employee();
    emp.setName(dto.getName());
    emp.setDepartment(dto.getDepartment());
    emp.setRole(dto.getRole());
    emp.setEmail(dto.getEmail());
    emp.setPhone(dto.getPhone());
    emp.setPassword(dto.getPassword());
    emp.setAge(dto.getAge() == null ? 18 : dto.getAge());
    emp.setDateOfJoining(dto.getDateOfJoining());
    emp.setEmployeeStatus(dto.getEmployeeStatus());
    emp.setProfilePhotoUrl(dto.getProfilePhotoUrl()); // fixed
    emp.setAddress(dto.getAddress()); // fixed
    emp.setPolicies(policies);
    return emp;
}



    public static PolicyDTO toPolicyDTO(Insurance insurance) {
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
}
