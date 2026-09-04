package com.sunrise.dentalclinic.dao;
import com.sunrise.dentalclinic.model.Appointment;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
public class AppointmentDAO {
    private final Connection connection;
    public AppointmentDAO() {
        this.connection = DBConnectionManager.getInstance().getConnection();
    }
    /**
     * Inserts a new appointment. appointment_number is left NULL here —
     * the BEFORE INSERT trigger in schema.sql fills it in automatically.
     */
    public int insert(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointments (patient_id, dentist_id, treatment_id, appointment_date, appointment_time, status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, appointment.getPatientId());
            stmt.setInt(2, appointment.getDentistId());
            stmt.setInt(3, appointment.getTreatmentId());
            stmt.setDate(4, Date.valueOf(appointment.getAppointmentDate()));
            stmt.setTime(5, Time.valueOf(appointment.getAppointmentTime()));
            stmt.setString(6, appointment.getStatus().name());
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        throw new SQLException("Insert failed, no ID obtained for appointment.");
    }
    /** Core requirement from the brief: "Search using the appointment number." */
    public Appointment findByAppointmentNumber(String appointmentNumber) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE appointment_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, appointmentNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }
    public Appointment findById(int appointmentId) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE appointment_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, appointmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }
    /** Powers a "Today's Appointments" report — a nice bonus feature to mention in your report. */
    public List<Appointment> findByDate(LocalDate date) throws SQLException {
        List<Appointment> results = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE appointment_date = ? ORDER BY appointment_time";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(date));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapRow(rs));
                }
            }
        }
        return results;
    }
    public List<Appointment> findAll() throws SQLException {
        List<Appointment> results = new ArrayList<>();
        String sql = "SELECT * FROM appointments ORDER BY appointment_date DESC, appointment_time DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                results.add(mapRow(rs));
            }
        }
        return results;
    }
    public boolean updateStatus(int appointmentId, Appointment.Status status) throws SQLException {
        String sql = "UPDATE appointments SET status = ? WHERE appointment_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status.name());
            stmt.setInt(2, appointmentId);
            return stmt.executeUpdate() > 0;
        }
    }
    private Appointment mapRow(ResultSet rs) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(rs.getInt("appointment_id"));
        appointment.setAppointmentNumber(rs.getString("appointment_number"));
        appointment.setPatientId(rs.getInt("patient_id"));
        appointment.setDentistId(rs.getInt("dentist_id"));
        appointment.setTreatmentId(rs.getInt("treatment_id"));
        appointment.setAppointmentDate(rs.getDate("appointment_date").toLocalDate());
        appointment.setAppointmentTime(rs.getTime("appointment_time").toLocalTime());
        appointment.setStatus(Appointment.Status.valueOf(rs.getString("status")));
        return appointment;
    }
}