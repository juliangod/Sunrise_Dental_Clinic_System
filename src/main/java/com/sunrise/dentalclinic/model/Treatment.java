package com.sunrise.dentalclinic.model;

import java.math.BigDecimal;

public class Treatment {

    private int treatmentId;
    private String treatmentName;
    private BigDecimal baseCost;

    public Treatment() {
    }

    public Treatment(int treatmentId, String treatmentName, BigDecimal baseCost) {
        this.treatmentId = treatmentId;
        this.treatmentName = treatmentName;
        this.baseCost = baseCost;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public BigDecimal getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(BigDecimal baseCost) {
        this.baseCost = baseCost;
    }

    @Override
    public String toString() {
        return treatmentName + " (Rs. " + baseCost + ")";
    }
}
