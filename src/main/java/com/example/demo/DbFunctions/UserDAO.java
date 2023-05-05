package com.example.demo.DbFunctions;

import com.example.demo.Models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private Connection connection;

    public UserDAO() {
        String url = Variables.DB_URL;
        String user = Variables.DB_USER;
        String password = Variables.DB_PASSWORD;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error connecting to database");
            e.printStackTrace();
        }
    }

    public boolean userExists(String username) {
        String sql = "SELECT * FROM users WHERE user_login = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error executing query");
            e.printStackTrace();
        }
        return false;
    }

    public boolean emailExists(String email) {
        String sql = "SELECT * FROM users WHERE user_email = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error executing query");
            e.printStackTrace();
        }
        return false;
    }

    public boolean getUserValidating(String login, String password) {

        String log = login;
        String pass = password;

        String sql = "SELECT * FROM users WHERE user_login = ? AND user_password = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, log);
            statement.setString(2, pass);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error executing query");
            e.printStackTrace();
        }
        return false;
    }
}