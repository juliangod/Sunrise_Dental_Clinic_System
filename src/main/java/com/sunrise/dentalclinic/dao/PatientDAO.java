package com.sunrise.dentalclinic.dao;

import com.sunrise.dentalclinic.model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pattern: all SQL for the `patients` table lives here and nowhere else.
 * Every method uses a PreparedStatement (never string-concatenated SQL) —
 * this is what stops SQL injection and ties into the "Ethical" EDGE criterion.
 */
public class PatientDAO {

    private final Connection connection;

    public PatientDAO() {
        this.connection = DBConnectionManager.getInstance().getConnection();
    }

    /** Inserts a new patient and returns the generated patient_id. */
    public int insert(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (full_name, address, contact_number) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, patient.getFullName());
            stmt.setString(2, patient.getAddress());
            stmt.setString(3, patient.getContactNumber());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        throw new SQLException("Insert failed, no ID obtained for patient: " + patient.getFullName());
    }

    public Patient findById(int patientId) throws SQLException {
        String sql = "SELECT * FROM patients WHERE patient_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, patientId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public List<Patient> findAll() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients ORDER BY full_name";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                patients.add(mapRow(rs));
            }
        }
        return patients;
    }

    public boolean update(Patient patient) throws SQLException {
        String sql = "UPDATE patients SET full_name = ?, address = ?, contact_number = ? WHERE patient_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, patient.getFullName());
            stmt.setString(2, patient.getAddress());
            stmt.setString(3, patient.getContactNumber());
            stmt.setInt(4, patient.getPatientId());
            return stmt.executeUpdate() > 0;
        }
    }

    private Patient mapRow(ResultSet rs) throws SQLException {
        return new Patient(
                rs.getInt("patient_id"),
                rs.getString("full_name"),
                rs.getString("address"),
                rs.getString("contact_number")
        );
    }
}
