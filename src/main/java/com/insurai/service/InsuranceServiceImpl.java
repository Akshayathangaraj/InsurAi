package com.insurai.insurai.service;

import com.insurai.insurai.exception.ResourceNotFoundException;
import com.insurai.insurai.model.Insurance;
import com.insurai.insurai.repository.EmployeeRepository;
import com.insurai.insurai.repository.InsuranceRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import com.insurai.insurai.exception.PolicyInUseException;


import java.util.List;

@Service
public class InsuranceServiceImpl implements InsuranceService {

    private final InsuranceRepository repo;
    private final EmployeeRepository employeeRepo;

    public InsuranceServiceImpl(InsuranceRepository repo, EmployeeRepository employeeRepo) {
        this.repo = repo;
        this.employeeRepo = employeeRepo;
    }

    @Override
    public List<Insurance> getAllPolicies() {
        return repo.findAll();
    }

    @Override
    public Insurance getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance ID " + id + " not found"));
    }

    @Override
    public Insurance savePolicy(Insurance insurance) {
        return repo.save(insurance);
    }

    @Override
    public Insurance updatePolicy(Long id, Insurance updates) {
        Insurance existing = getById(id);
        existing.setPolicyName(updates.getPolicyName());
        existing.setHolderName(updates.getHolderName());
        existing.setPremium(updates.getPremium());
        return repo.save(existing);
    }

    @Override
public void deletePolicy(Long id) {
    Insurance existing = getById(id);

    if (existing.getEmployees() != null && !existing.getEmployees().isEmpty()) {
        throw new PolicyInUseException(
            "Cannot delete policy. It is assigned to " + existing.getEmployees().size() + " employee(s)."
        );
    }

    repo.delete(existing);
}


}
