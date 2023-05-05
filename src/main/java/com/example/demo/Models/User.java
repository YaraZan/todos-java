package com.example.demo.Models;

import java.util.ArrayList;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Objects;

public class User {

    int id;

    String login;

    String email;

    String password;

    ArrayList<Todo> todos;


    public User(String user_login, String user_email, String user_password) {

        this.login = user_login;
        this.email = user_email;
        this.password = user_password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTodos(ArrayList<Todo> todos) { this.todos = todos; }
    public ArrayList<Todo> getTodos() { return todos; }

}

