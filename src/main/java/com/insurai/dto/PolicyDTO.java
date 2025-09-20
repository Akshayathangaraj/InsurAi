package com.insurai.insurai.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class PolicyDTO {

    private Long id;
    private String policyName;
    private String policyType;
    private Double premium;
    private Double coverageAmount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String status;

    // 🔥 New fields
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate renewalDate;

    private Integer claimLimit;
    private String provider;
    private String riskLevel;
    private Integer gracePeriod;
    private String termsAndConditions;

    // No-arg constructor
    public PolicyDTO() {}

    // Full constructor
    public PolicyDTO(Long id, String policyName, String policyType, Double premium,
                     Double coverageAmount, LocalDate startDate, LocalDate endDate,
                     String status, LocalDate renewalDate, Integer claimLimit,
                     String provider, String riskLevel, Integer gracePeriod,
                     String termsAndConditions) {
        this.id = id;
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
}
