package com.insurai.insurai.service;

import com.insurai.insurai.model.Insurance;
import java.util.List;

public interface InsuranceService {
    List<Insurance> getAllPolicies();
    Insurance getById(Long id);
    Insurance savePolicy(Insurance insurance); // renamed to match controller
    Insurance updatePolicy(Long id, Insurance updates);
    void deletePolicy(Long id);
}
