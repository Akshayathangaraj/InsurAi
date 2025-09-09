package com.insurai.insurai.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
    name = "employee",
    uniqueConstraints = @UniqueConstraint(columnNames = "email")
)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String department;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    private String password;

    @Column(nullable = false)
    private int age;

    // 🔥 New fields
    private LocalDate dateOfJoining;
    private String employeeStatus; // Active, On Leave, Resigned
    private LocalDateTime lastLogin;
    private String profilePhotoURL;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "employee_policies",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "policy_id")
    )
    private List<Insurance> policies;

    public Employee() {}

    public Employee(String name, String department, String role, String email, String phone,
                    String password, int age, LocalDate dateOfJoining, String employeeStatus,
                    LocalDateTime lastLogin, String profilePhotoURL, List<Insurance> policies) {
        this.name = name;
        this.department = department;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.password = password;
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

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public LocalDate getDateOfJoining() { return dateOfJoining; }
    public void setDateOfJoining(LocalDate dateOfJoining) { this.dateOfJoining = dateOfJoining; }

    public String getEmployeeStatus() { return employeeStatus; }
    public void setEmployeeStatus(String employeeStatus) { this.employeeStatus = employeeStatus; }

    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }

    public String getProfilePhotoURL() { return profilePhotoURL; }
    public void setProfilePhotoURL(String profilePhotoURL) { this.profilePhotoURL = profilePhotoURL; }

    public List<Insurance> getPolicies() { return policies; }
    public void setPolicies(List<Insurance> policies) { this.policies = policies; }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", dateOfJoining=" + dateOfJoining +
                ", employeeStatus='" + employeeStatus + '\'' +
                ", lastLogin=" + lastLogin +
                ", profilePhotoURL='" + profilePhotoURL + '\'' +
                ", policies=" + (policies != null ? policies.size() : 0) +
                '}';
    }
}
