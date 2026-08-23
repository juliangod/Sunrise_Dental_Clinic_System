package com.sunrise.dentalclinic.model;

/**
 * Represents a patient record.
 * Plain data-holder (POJO) — kept free of DB/UI logic on purpose,
 * so it can be reused by the DAO layer, the Swing UI, and the REST layer alike.
 */
public class Patient {

    private int patientId;
    private String fullName;
    private String address;
    private String contactNumber;

    public Patient() {
        // required no-arg constructor (used when building an object before saving it)
    }

    public Patient(int patientId, String fullName, String address, String contactNumber) {
        this.patientId = patientId;
        this.fullName = fullName;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    // Overload used before the patient has been saved (no ID yet)
    public Patient(String fullName, String address, String contactNumber) {
        this(-1, fullName, address, contactNumber);
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public String toString() {
        return "Patient{id=" + patientId + ", name='" + fullName + "', contact='" + contactNumber + "'}";
    }
}
