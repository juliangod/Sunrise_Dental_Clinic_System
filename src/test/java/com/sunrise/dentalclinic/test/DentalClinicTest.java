package com.sunrise.dentalclinic.test;

import com.sunrise.dentalclinic.dao.AppointmentDAO;
import com.sunrise.dentalclinic.dao.UserDAO;
import com.sunrise.dentalclinic.model.Appointment;
import com.sunrise.dentalclinic.model.User;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DentalClinicTest {

    private static UserDAO userDAO;
    private static AppointmentDAO appointmentDAO;

    @BeforeAll
    public static void setUp() {
        userDAO = new UserDAO();
        appointmentDAO = new AppointmentDAO();
    }

    @Test
    @Order(1)
    @DisplayName("TC-01: Verify User Search by Existing Username")
    public void testFindUserByValidUsername() throws SQLException {
        User user = userDAO.findByUsername("admin");
        assertNotNull(user, "User 'admin' should be found in the database.");
    }

    @Test
    @Order(2)
    @DisplayName("TC-02: Verify Null Output for Non-Existent Username")
    public void testFindUserByInvalidUsername() throws SQLException {
        User user = userDAO.findByUsername("non_existent_user_999");
        assertNull(user, "Searching for a non-existent user should return null.");
    }

    @Test
    @Order(3)
    @DisplayName("TC-03: Verify Appointment Retrieval by Valid Number")
    public void testFindAppointmentByValidNumber() throws SQLException {
        Appointment appt = appointmentDAO.findByAppointmentNumber("APT-000001");
        assertNotNull(appt, "Appointment APT-000001 must exist in target database.");
    }

    @Test
    @Order(4)
    @DisplayName("TC-04: Verify Null Response for Non-Existent Appointment")
    public void testFindAppointmentByInvalidNumber() throws SQLException {
        Appointment appt = appointmentDAO.findByAppointmentNumber("APT-999999");
        assertNull(appt, "Non-existent appointment number must return null.");
    }
}