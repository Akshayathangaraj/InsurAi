package com.insurai.insurai.repository;

import com.insurai.insurai.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Check if any employee is linked to this insurance policy
    boolean existsByPolicies_Id(Long policyId);

    // For login
    boolean existsByEmail(String email);
    Employee findByEmail(String email);
    boolean existsByPhone(String phone);
}
