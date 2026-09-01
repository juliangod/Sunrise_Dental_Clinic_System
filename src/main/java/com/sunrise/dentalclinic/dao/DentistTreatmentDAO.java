package com.sunrise.dentalclinic.dao;

import com.sunrise.dentalclinic.model.DentistTreatment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DentistTreatmentDAO {

    private final Connection connection;

    public DentistTreatmentDAO() {
        this.connection = DBConnectionManager.getInstance().getConnection();
    }

    public List<DentistTreatment> findByDentistId(int dentistId) throws SQLException {
        List<DentistTreatment> results = new ArrayList<>();

        String sql = "SELECT dt.dentist_treatment_id, dt.dentist_id, dt.treatment_id, "
                + "t.treatment_name, dt.price "
                + "FROM dentist_treatments dt "
                + "JOIN treatments t ON dt.treatment_id = t.treatment_id "
                + "WHERE dt.dentist_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, dentistId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DentistTreatment dt = new DentistTreatment(
                            rs.getInt("dentist_treatment_id"),
                            rs.getInt("dentist_id"),
                            rs.getInt("treatment_id"),
                            rs.getString("treatment_name"),
                            rs.getDouble("price")
                    );
                    results.add(dt);
                }
            }
        }

        return results;
    }
}