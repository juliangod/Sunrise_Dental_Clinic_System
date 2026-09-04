package com.sunrise.dentalclinic.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sunrise.dentalclinic.dao.DBConnectionManager;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Lightweight REST Server implementation using com.sun.net.httpserver.
 * Exposes internal database queries over HTTP/REST endpoints without external dependencies.
 */
public class RestServer {

    private static HttpServer server;
    private static final int PORT = 8080;

    public static synchronized void startServer() {
        if (server != null) {
            return; // Guard against duplicate instances
        }
        try {
            server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.createContext("/api/appointments/", new AppointmentHandler());
            server.setExecutor(null); // Default HTTP executor
            server.start();
            System.out.println("REST API Server initialized on http://localhost:" + PORT);
        } catch (IOException e) {
            System.err.println("REST Server initialization failure: " + e.getMessage());
        }
    }

    public static synchronized void stopServer() {
        if (server != null) {
            server.stop(0);
            server = null;
            System.out.println("REST API Server shut down.");
        }
    }

    static class AppointmentHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Enable CORS
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");

            if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendResponse(exchange, 405, "{\"error\": \"Method Not Allowed\"}");
                return;
            }

            String path = exchange.getRequestURI().getPath();
            String[] pathParts = path.split("/");

            if (pathParts.length < 4 || pathParts[3].trim().isEmpty()) {
                sendResponse(exchange, 400, "{\"error\": \"Missing or malformed appointment reference\"}");
                return;
            }

            String appointmentNumber = pathParts[3].trim();
            String sql = "SELECT * FROM appointments WHERE appointment_number = ?";

            try (Connection conn = DBConnectionManager.getInstance().getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, appointmentNumber);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String jsonResponse = String.format(
                            "{\"appointmentNumber\":\"%s\",\"patientId\":%d,\"dentistId\":%d,\"appointmentDate\":\"%s\",\"appointmentTime\":\"%s\",\"status\":\"%s\"}",
                            escapeJson(rs.getString("appointment_number")),
                            rs.getInt("patient_id"),
                            rs.getInt("dentist_id"),
                            rs.getString("appointment_date"),
                            rs.getString("appointment_time"),
                            escapeJson(rs.getString("status"))
                        );
                        sendResponse(exchange, 200, jsonResponse);
                    } else {
                        sendResponse(exchange, 404, "{\"error\": \"Appointment reference not found\"}");
                    }
                }
            } catch (Exception e) {
                sendResponse(exchange, 500, "{\"error\": \"Database connection error: " + escapeJson(e.getMessage()) + "\"}");
            }
        }

        private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
            byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(statusCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }

        private String escapeJson(String input) {
            if (input == null) return "";
            return input.replace("\\", "\\\\").replace("\"", "\\\"");
        }
    }
}