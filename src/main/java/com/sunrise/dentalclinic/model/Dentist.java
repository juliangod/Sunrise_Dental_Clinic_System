package com.sunrise.dentalclinic.model;

public class Dentist {

    private int dentistId;
    private String fullName;
    private String specialty;

    public Dentist() {
    }

    public Dentist(int dentistId, String fullName, String specialty) {
        this.dentistId = dentistId;
        this.fullName = fullName;
        this.specialty = specialty;
    }

    public int getDentistId() {
        return dentistId;
    }

    public void setDentistId(int dentistId) {
        this.dentistId = dentistId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public String toString() {
        // Used directly in JComboBox lists in the UI, so keep it human-readable
        return fullName + (specialty != null ? " (" + specialty + ")" : "");
    }
}
