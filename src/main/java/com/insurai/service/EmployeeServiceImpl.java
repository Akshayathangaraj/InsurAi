package com.insurai.insurai.service;

import com.insurai.insurai.exception.ResourceNotFoundException;
import com.insurai.insurai.model.Employee;
import com.insurai.insurai.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repo;

    public EmployeeServiceImpl(EmployeeRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }

    @Override
    public Employee getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee ID " + id + " not found"));
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return repo.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee updates) {
        Employee existing = getById(id);

        existing.setName(updates.getName());
        existing.setDepartment(updates.getDepartment());
        existing.setEmail(updates.getEmail());
        existing.setAge(updates.getAge());
        existing.setPolicies(updates.getPolicies()); 

        return repo.save(existing);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee existing = getById(id);
        repo.delete(existing);
    }
}
