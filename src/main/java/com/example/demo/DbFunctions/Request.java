package com.example.demo.DbFunctions;

import com.example.demo.Models.Todo;
import com.example.demo.Models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Request {

    public static void registrateUser(String userLogin, String userEmail, String userPassword) {

        String login = userLogin;
        String email= userEmail;
        String pass = userPassword;

        // query
        String query = "INSERT INTO users(user_login, user_email, user_password) VALUES(?, ?, ?)";

        try {
            PreparedStatement pst = DriverManager.getConnection(Variables.DB_URL, Variables.DB_USER, Variables.DB_PASSWORD).prepareStatement(query);

            pst.setString(1, login);
            pst.setString(2, email);
            pst.setString(3, pass);
            pst.executeUpdate();

            Variables.ACTIVE_USER = new User(login, email, pass);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Request.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

    public static boolean authorizeUser(String userLogin, String userPassword) {

        String uLog = userLogin;
        String uPass = userPassword;

        String query = "SELECT * FROM users WHERE user_login = ?";

        if ((new UserDAO().getUserValidating(uLog, uPass))) {
            try {
                PreparedStatement statement = DriverManager.getConnection(Variables.DB_URL, Variables.DB_USER, Variables.DB_PASSWORD).prepareStatement(query);
                statement.setString(1, uLog);
                ResultSet result = statement.executeQuery();

                result.next();
                int id = result.getInt("user_id");
                String login = result.getString("user_login");
                String email = result.getString("user_email");
                String pass = result.getString("user_password");
                System.out.println("Successfully Authorized!");

                Variables.ACTIVE_USER = new User(login, email, pass);
                Variables.ACTIVE_USER.setId(id);
                Variables.ACTIVE_USER.setTodos(getTodosForUser(id));

                return true;

            } catch (SQLException e) {
                System.out.println("Error executing query");
                e.printStackTrace();
            }
        } else {
            System.out.println("Неверные данные");
        }

        return false;
    }

    public static ArrayList<Todo> getTodosForUser(int userId) throws SQLException {
        ArrayList<Todo> todos = new ArrayList<>();
        String sql = "SELECT * FROM todos where user_id = ?";
        try (Connection conn = DriverManager.getConnection(Variables.DB_URL, Variables.DB_USER, Variables.DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("todo_id");
                int uid = rs.getInt("user_id");
                String name = rs.getString("todo_name");
                String descr = rs.getString("todo_descr");
                String deadline = rs.getString("todo_deadline");
                Boolean isDone = rs.getBoolean("isDone");
                Boolean isDeleted = rs.getBoolean("isDeleted");
                Timestamp createdAtTimestamp = rs.getTimestamp("createdAtTimestamp");
                Todo todo = new Todo(id, uid, name, descr, deadline, isDone, isDeleted);
                todo.setCreatedAtTimestamp(createdAtTimestamp);
                todos.add(todo);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return todos;
    }



}