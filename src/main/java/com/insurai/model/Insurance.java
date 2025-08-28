package com.insurai.insurai.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "insurance",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"policyName", "holderName"})
    }
) // Prevents duplicate policyName + holderName
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String policyName;
    private String holderName;
    private Double premium; // Wrapper class preferred

    // Default constructor (required by JPA)
    public Insurance() {}

    // Parameterized constructor (optional, for easy object creation)
    public Insurance(String policyName, String holderName, Double premium) {
        this.policyName = policyName;
        this.holderName = holderName;
        this.premium = premium;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPolicyName() { return policyName; }
    public void setPolicyName(String policyName) { this.policyName = policyName; }

    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }

    public Double getPremium() { return premium; }
    public void setPremium(Double premium) { this.premium = premium; }

    // Optional - For easy debugging/logging
    @Override
    public String toString() {
        return "Insurance{" +
                "id=" + id +
                ", policyName='" + policyName + '\'' +
                ", holderName='" + holderName + '\'' +
                ", premium=" + premium +
                '}';
    }
}
