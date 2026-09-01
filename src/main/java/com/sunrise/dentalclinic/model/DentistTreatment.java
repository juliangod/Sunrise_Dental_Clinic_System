package com.sunrise.dentalclinic.model;

public class DentistTreatment {

    private int dentistTreatmentId;
    private int dentistId;
    private int treatmentId;
    private String treatmentName;
    private double price;

    public DentistTreatment(int dentistTreatmentId, int dentistId, int treatmentId,
                             String treatmentName, double price) {
        this.dentistTreatmentId = dentistTreatmentId;
        this.dentistId = dentistId;
        this.treatmentId = treatmentId;
        this.treatmentName = treatmentName;
        this.price = price;
    }

    public int getDentistTreatmentId() {
        return dentistTreatmentId;
    }

    public int getDentistId() {
        return dentistId;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return treatmentName + " (Rs. " + String.format("%.2f", price) + ")";
    }
}