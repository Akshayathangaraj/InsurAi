package com.insurai.insurai.dto;

public class PolicyDTO {
    private Long id;
    private String policyName;
    private String holderName;
    private Double premium;

    public PolicyDTO() {}

    public PolicyDTO(Long id, String policyName, String holderName, Double premium) {
        this.id = id;
        this.policyName = policyName;
        this.holderName = holderName;
        this.premium = premium;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPolicyName() { return policyName; }
    public void setPolicyName(String policyName) { this.policyName = policyName; }

    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }

    public Double getPremium() { return premium; }
    public void setPremium(Double premium) { this.premium = premium; }
}
