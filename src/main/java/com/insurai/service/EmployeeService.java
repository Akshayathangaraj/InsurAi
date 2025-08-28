package com.insurai.insurai.service;

import com.insurai.insurai.model.Employee;
import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee getById(Long id);

    Employee saveEmployee(Employee employee);

    Employee updateEmployee(Long id, Employee updates);

    void deleteEmployee(Long id);
}
