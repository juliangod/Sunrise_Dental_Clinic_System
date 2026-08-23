package com.sunrise.dentalclinic.dao;

import com.sunrise.dentalclinic.model.User;

import java.sql.*;

public class UserDAO {

    private final Connection connection;

    public UserDAO() {
        this.connection = DBConnectionManager.getInstance().getConnection();
    }

    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("password"), // this column actually stores the HASH, see AuthService
                            rs.getString("full_name"),
                            rs.getString("role")
                    );
                }
            }
        }
        return null;
    }
}
