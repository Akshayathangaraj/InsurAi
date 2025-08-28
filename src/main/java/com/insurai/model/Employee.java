package com.insurai.insurai.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String department;
    private String email;
    private int age;

   @ManyToMany(fetch = FetchType.EAGER)
@JoinTable(
    name = "employee_policies",
    joinColumns = @JoinColumn(name = "employee_id"),
    inverseJoinColumns = @JoinColumn(name = "policy_id")
)
private List<Insurance> policies;
    public Employee() {}
    public Employee(String name, String department, String email, int age, List<Insurance> policies) {
        this.name = name;
        this.department = department;
        this.email = email;
        this.age = age;
        this.policies = policies;
    }
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getEmail() { return email; }
    public int getAge() { return age; }
    public List<Insurance> getPolicies() { return policies; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setEmail(String email) { this.email = email; }
    public void setAge(int age) { this.age = age; }
    public void setPolicies(List<Insurance> policies) { this.policies = policies; }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", policies=" + policies +
                '}';
    }
}
