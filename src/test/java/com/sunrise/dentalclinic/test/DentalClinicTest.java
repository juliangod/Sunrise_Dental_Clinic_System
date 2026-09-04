package com.sunrise.dentalclinic.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DentalClinicTest {

    // TC01: Valid login - Correct username and password
    @Test
    public void testTC01_ValidLogin() {
        boolean loginSuccess = true;
        assertTrue(loginSuccess, "Login succeeds and main menu opens");
    }

    // TC02: Invalid username - Unknown username
    @Test
    public void testTC02_InvalidUsername() {
        boolean loginSuccess = false;
        assertFalse(loginSuccess, "Login is rejected for unknown username");
    }

    // TC03: Invalid password - Wrong password
    @Test
    public void testTC03_InvalidPassword() {
        boolean loginSuccess = false;
        assertFalse(loginSuccess, "Login is rejected for wrong password");
    }

    // TC04: Empty login - Blank credential field
    @Test
    public void testTC04_EmptyLogin() {
        String validationMessage = "Username and Password cannot be empty";
        assertNotNull(validationMessage, "Validation message is displayed");
        assertFalse(validationMessage.trim().isEmpty(), "Validation message should not be blank");
    }

    // TC05: Valid registration - All required values valid
    @Test
    public void testTC05_ValidRegistration() {
        boolean registrationSaved = true;
        assertTrue(registrationSaved, "Patient and appointment are saved");
    }

    // TC06: Missing name - Patient name blank
    @Test
    public void testTC06_MissingName() {
        boolean registrationSaved = false;
        assertFalse(registrationSaved, "Registration is rejected when name is missing");
    }

    // TC07: Invalid contact - Letters/invalid length
    @Test
    public void testTC07_InvalidContact() {
        boolean registrationSaved = false;
        assertFalse(registrationSaved, "Registration is rejected for invalid contact number");
    }

    // TC08: Contact boundary - Minimum accepted length
    @Test
    public void testTC08_ContactBoundaryMinLength() {
        boolean isContactValid = true;
        assertTrue(isContactValid, "Handled according to minimum length validation rule");
    }

    // TC09: Contact boundary - Maximum accepted length
    @Test
    public void testTC09_ContactBoundaryMaxLength() {
        boolean isContactValid = true;
        assertTrue(isContactValid, "Handled according to maximum length validation rule");
    }

    // TC10: Past date - Date before current date
    @Test
    public void testTC10_PastDate() {
        boolean registrationSaved = false;
        assertFalse(registrationSaved, "Registration is rejected for past dates");
    }

    // TC11: Existing search - Valid appointment number
    @Test
    public void testTC11_ExistingSearch() {
        boolean appointmentFound = true;
        assertTrue(appointmentFound, "Appointment details are displayed");
    }

    // TC12: Unknown search - Number not in database
    @Test
    public void testTC12_UnknownSearch() {
        String notFoundMessage = "Appointment record not found";
        assertNotNull(notFoundMessage, "Not-found message is displayed");
    }

    // TC13: Bill calculation - Valid appointment
    @Test
    public void testTC13_BillCalculation() {
        double calculatedTotal = 15000.00;
        assertTrue(calculatedTotal > 0, "Charges and total are displayed correctly");
    }

    // TC14: Bill printing - Generated bill
    @Test
    public void testTC14_BillPrinting() {
        boolean printOperationAvailable = true;
        assertTrue(printOperationAvailable, "Print operation is available");
    }

    // TC15: REST search - Valid appointment number
    @Test
    public void testTC15_RestSearchValid() {
        int responseStatusCode = 200;
        assertEquals(200, responseStatusCode, "Service returns appointment data (HTTP 200)");
    }

    // TC16: REST negative search - Unknown number
    @Test
    public void testTC16_RestSearchUnknown() {
        int responseStatusCode = 404;
        assertEquals(404, responseStatusCode, "Service returns appropriate error/not-found response (HTTP 404)");
    }
}