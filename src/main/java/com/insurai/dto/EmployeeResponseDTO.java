package com.insurai.insurai.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class EmployeeResponseDTO {

    private Long id;
    private String name;
    private String department;
    private String role;
    private String email;
    private String phone;
    private Integer age;
    private LocalDate dateOfJoining;
    private String employeeStatus;
    private LocalDateTime lastLogin;
    private String profilePhotoURL;
    private List<PolicyDTO> policies;

    public EmployeeResponseDTO() {}

    // Full constructor
    public EmployeeResponseDTO(Long id, String name, String department, String role,
                               String email, String phone, Integer age, LocalDate dateOfJoining,
                               String employeeStatus, LocalDateTime lastLogin, String profilePhotoURL,
                               List<PolicyDTO> policies) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.dateOfJoining = dateOfJoining;
        this.employeeStatus = employeeStatus;
        this.lastLogin = lastLogin;
        this.profilePhotoURL = profilePhotoURL;
        this.policies = policies;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public LocalDate getDateOfJoining() { return dateOfJoining; }
    public void setDateOfJoining(LocalDate dateOfJoining) { this.dateOfJoining = dateOfJoining; }

    public String getEmployeeStatus() { return employeeStatus; }
    public void setEmployeeStatus(String employeeStatus) { this.employeeStatus = employeeStatus; }

    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }

    public String getProfilePhotoURL() { return profilePhotoURL; }
    public void setProfilePhotoURL(String profilePhotoURL) { this.profilePhotoURL = profilePhotoURL; }

    public List<PolicyDTO> getPolicies() { return policies; }
    public void setPolicies(List<PolicyDTO> policies) { this.policies = policies; }
}
