package com.sunrise.dentalclinic.dao;

import com.sunrise.dentalclinic.model.Dentist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DentistDAO {

    private final Connection connection;

    public DentistDAO() {
        this.connection = DBConnectionManager.getInstance().getConnection();
    }

    public List<Dentist> findAll() throws SQLException {
        List<Dentist> dentists = new ArrayList<>();
        String sql = "SELECT * FROM dentists ORDER BY full_name";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                dentists.add(new Dentist(
                        rs.getInt("dentist_id"),
                        rs.getString("full_name"),
                        rs.getString("specialty")
                ));
            }
        }
        return dentists;
    }

    public Dentist findById(int dentistId) throws SQLException {
        String sql = "SELECT * FROM dentists WHERE dentist_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, dentistId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Dentist(rs.getInt("dentist_id"), rs.getString("full_name"), rs.getString("specialty"));
                }
            }
        }
        return null;
    }
}