package com.sunrise.dentalclinic.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Bill {

    private int billId;
    private int appointmentId;
    private BigDecimal consultationFee;
    private BigDecimal treatmentCost;
    private BigDecimal totalAmount;
    private LocalDateTime generatedOn;

    public Bill() {
    }

    public Bill(int appointmentId, BigDecimal consultationFee, BigDecimal treatmentCost) {
        this.appointmentId = appointmentId;
        this.consultationFee = consultationFee;
        this.treatmentCost = treatmentCost;
        this.totalAmount = consultationFee.add(treatmentCost);
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public BigDecimal getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(BigDecimal consultationFee) {
        this.consultationFee = consultationFee;
    }

    public BigDecimal getTreatmentCost() {
        return treatmentCost;
    }

    public void setTreatmentCost(BigDecimal treatmentCost) {
        this.treatmentCost = treatmentCost;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getGeneratedOn() {
        return generatedOn;
    }

    public void setGeneratedOn(LocalDateTime generatedOn) {
        this.generatedOn = generatedOn;
    }

    @Override
    public String toString() {
        return "Bill{appointmentId=" + appointmentId + ", total=Rs. " + totalAmount + "}";
    }
}
