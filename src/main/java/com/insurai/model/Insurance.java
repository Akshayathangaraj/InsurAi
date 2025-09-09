package com.insurai.insurai.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(
    name = "insurance",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"policyName", "policyType"})
    }
)
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String policyName;
    private String policyType; // Health, Life, Vehicle, etc.
    private Double premium;
    private Double coverageAmount; // Total coverage
    private LocalDate startDate;
    private LocalDate endDate;
    private String status; // Active, Expired, Claimed

    // 🔥 New fields
    private LocalDate renewalDate;
    private Integer claimLimit; // max claims allowed per year
    private String provider; // insurance company name
    private String riskLevel; // Low, Medium, High
    private Integer gracePeriod; // days after expiry for renewal
    private String termsAndConditions;

    @ManyToMany(mappedBy = "policies", fetch = FetchType.LAZY)
    private List<Employee> employees;

    public Insurance() {}

    public Insurance(String policyName, String policyType, Double premium,
                     Double coverageAmount, LocalDate startDate, LocalDate endDate,
                     String status, LocalDate renewalDate, Integer claimLimit,
                     String provider, String riskLevel, Integer gracePeriod,
                     String termsAndConditions) {
        this.policyName = policyName;
        this.policyType = policyType;
        this.premium = premium;
        this.coverageAmount = coverageAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.renewalDate = renewalDate;
        this.claimLimit = claimLimit;
        this.provider = provider;
        this.riskLevel = riskLevel;
        this.gracePeriod = gracePeriod;
        this.termsAndConditions = termsAndConditions;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPolicyName() { return policyName; }
    public void setPolicyName(String policyName) { this.policyName = policyName; }

    public String getPolicyType() { return policyType; }
    public void setPolicyType(String policyType) { this.policyType = policyType; }

    public Double getPremium() { return premium; }
    public void setPremium(Double premium) { this.premium = premium; }

    public Double getCoverageAmount() { return coverageAmount; }
    public void setCoverageAmount(Double coverageAmount) { this.coverageAmount = coverageAmount; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getRenewalDate() { return renewalDate; }
    public void setRenewalDate(LocalDate renewalDate) { this.renewalDate = renewalDate; }

    public Integer getClaimLimit() { return claimLimit; }
    public void setClaimLimit(Integer claimLimit) { this.claimLimit = claimLimit; }

    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }

    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }

    public Integer getGracePeriod() { return gracePeriod; }
    public void setGracePeriod(Integer gracePeriod) { this.gracePeriod = gracePeriod; }

    public String getTermsAndConditions() { return termsAndConditions; }
    public void setTermsAndConditions(String termsAndConditions) { this.termsAndConditions = termsAndConditions; }

    public List<Employee> getEmployees() { return employees; }
    public void setEmployees(List<Employee> employees) { this.employees = employees; }

    @Override
    public String toString() {
        return "Insurance{" +
                "id=" + id +
                ", policyName='" + policyName + '\'' +
                ", policyType='" + policyType + '\'' +
                ", premium=" + premium +
                ", coverageAmount=" + coverageAmount +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                ", renewalDate=" + renewalDate +
                ", claimLimit=" + claimLimit +
                ", provider='" + provider + '\'' +
                ", riskLevel='" + riskLevel + '\'' +
                ", gracePeriod=" + gracePeriod +
                '}';
    }
}
