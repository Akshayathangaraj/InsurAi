package com.insurai.insurai.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public class EmployeeRequestDTO {

    @NotBlank
    private String name;

    private String department;

    @Email
    @NotBlank
    private String email;

    @Min(18)
    private Integer age;

    // list of policy ids to link
    private List<Long> policyIds;

    public EmployeeRequestDTO() {}

    // getters & setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public List<Long> getPolicyIds() { return policyIds; }
    public void setPolicyIds(List<Long> policyIds) { this.policyIds = policyIds; }
}
