package com.sunrise.dentalclinic.dao;

import com.sunrise.dentalclinic.model.Treatment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TreatmentDAO {

    private final Connection connection;

    public TreatmentDAO() {
        this.connection = DBConnectionManager.getInstance().getConnection();
    }

    public List<Treatment> findAll() throws SQLException {
        List<Treatment> treatments = new ArrayList<>();
        String sql = "SELECT * FROM treatments ORDER BY treatment_name";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                treatments.add(new Treatment(
                        rs.getInt("treatment_id"),
                        rs.getString("treatment_name"),
                        rs.getBigDecimal("base_cost")
                ));
            }
        }
        return treatments;
    }

    public Treatment findById(int treatmentId) throws SQLException {
        String sql = "SELECT * FROM treatments WHERE treatment_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, treatmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Treatment(rs.getInt("treatment_id"), rs.getString("treatment_name"), rs.getBigDecimal("base_cost"));
                }
            }
        }
        return null;
    }
}