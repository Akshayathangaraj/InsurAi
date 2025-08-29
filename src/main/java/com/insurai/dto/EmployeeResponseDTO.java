package com.insurai.insurai.dto;

import java.util.List;

public class EmployeeResponseDTO {
    private Long id;
    private String name;
    private String department;
    private String email;
    private Integer age;
    private List<PolicyDTO> policies;

    public EmployeeResponseDTO() {}

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public List<PolicyDTO> getPolicies() { return policies; }
    public void setPolicies(List<PolicyDTO> policies) { this.policies = policies; }
}
