package com.example.demo.DbFunctions;

import com.example.demo.Models.Todo;
import com.example.demo.Models.User;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

public class TodosDAO {

    private Connection connection;

    public TodosDAO() {
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

    public static void addTodo(String todo_name, String todo_descr, String todo_deadline, Timestamp createdAtTimestamp) {

        String sql = "INSERT INTO todos(user_id, todo_name, todo_descr, todo_deadline, createdAtTimestamp) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = DriverManager.getConnection(Variables.DB_URL, Variables.DB_USER, Variables.DB_PASSWORD).prepareStatement(sql);
            statement.setInt(1, Variables.ACTIVE_USER.getId());
            statement.setString(2, todo_name);
            statement.setString(3, todo_descr);
            statement.setString(4, todo_deadline);
            statement.setTimestamp(5, createdAtTimestamp);
            statement.executeUpdate();


            ArrayList<Todo> todoArrayList = Request.getTodosForUser(Variables.ACTIVE_USER.getId());
            Variables.ACTIVE_USER.setTodos(todoArrayList);

            System.out.println("successfully added a new todo!");

        } catch (SQLException e) {
            System.out.println("Error executing query");
            e.printStackTrace();
        }
    }

    public static void deleteTodo(int todo_id) {

        ArrayList<Todo> todoArrayList = Variables.ACTIVE_USER.getTodos();
        for (Todo todo : todoArrayList) {
            if (todo.getId() == todo_id) {
                todo.setDeleted(true);
                break;
            }
        }
        Variables.ACTIVE_USER.setTodos(todoArrayList);

        String sql = "UPDATE todos SET isDeleted = true WHERE todo_id = ?";
        try {
            PreparedStatement statement = DriverManager.getConnection(Variables.DB_URL, Variables.DB_USER, Variables.DB_PASSWORD).prepareStatement(sql);
            statement.setInt(1, todo_id);
            statement.executeUpdate();
            System.out.println("successfully deleted todo!");

        } catch (SQLException e) {
            System.out.println("Error executing query");
            e.printStackTrace();
        }

    }

    public static void setDoneTodo(int todo_id) {

        ArrayList<Todo> todoArrayList = Variables.ACTIVE_USER.getTodos();
        for (Todo todo : todoArrayList) {
            if (todo.getId() == todo_id) {
                todo.setDone(true);
                break;
            }
        }
        Variables.ACTIVE_USER.setTodos(todoArrayList);

        String sql = "UPDATE todos SET isDone = true WHERE todo_id = ?";
        try {
            PreparedStatement statement = DriverManager.getConnection(Variables.DB_URL, Variables.DB_USER, Variables.DB_PASSWORD).prepareStatement(sql);
            statement.setInt(1, todo_id);
            statement.executeUpdate();
            System.out.println("successfully completed todo!");

        } catch (SQLException e) {
            System.out.println("Error executing query");
            e.printStackTrace();
        }

    }

    public static ArrayList<Todo> getClosestTodos(ArrayList<Todo> todos) {
        ArrayList<Todo> nearestTodos = new ArrayList<>();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        todos.sort(Comparator.comparingLong(todo -> Math.abs(todo.getCreatedAtTimestamp().getTime() - now.getTime())));

        for (Todo todo : todos) {
            if (todo.getDone() || todo.getDeleted()) {
                continue;
            }
            nearestTodos.add(todo);
            if (nearestTodos.size() == 15) {
                break;
            }
        }

        return nearestTodos;
    }

    public static ArrayList<Todo> getUncompletedTodos(ArrayList<Todo> todos) {
        ArrayList<Todo> uncompletedTodos = new ArrayList<>();

        for (Todo todo : todos) {
            if (!todo.getDone() && !todo.getDeleted()) {
                uncompletedTodos.add(todo);
            }
        }

        // Сортировка по дате создания (от более ранней до более поздней)
        Collections.sort(uncompletedTodos, new Comparator<Todo>() {
            @Override
            public int compare(Todo todo1, Todo todo2) {
                return todo1.getCreatedAtTimestamp().compareTo(todo2.getCreatedAtTimestamp());
            }
        });

        return uncompletedTodos;
    }

    public static ArrayList<Todo> getCompletedTodos(ArrayList<Todo> todos) {
        ArrayList<Todo> completedTodos = new ArrayList<>();

        for (Todo todo : todos) {
            if (todo.getDone() && !todo.getDeleted()) {
                completedTodos.add(todo);
            }
        }

        // Сортировка по дате создания (от более ранней до более поздней)
        Collections.sort(completedTodos, new Comparator<Todo>() {
            @Override
            public int compare(Todo todo1, Todo todo2) {
                return todo1.getCreatedAtTimestamp().compareTo(todo2.getCreatedAtTimestamp());
            }
        });

        return completedTodos;
    }

    public static int getTodoCountForMonth(int month) {
        ArrayList<Todo> todos = Variables.ACTIVE_USER.getTodos();
        int todosCount = 0;
        for (Todo todo : todos) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(todo.getCreatedAtTimestamp().getTime());
            int todoMonth = calendar.get(Calendar.MONTH) + 1;
            if (todoMonth == month) {
                todosCount++;
            }
        }
        return todosCount;
    }

    public static int getDoneTodoCountForMonth(int month) {
        ArrayList<Todo> todos = Variables.ACTIVE_USER.getTodos();
        int todosCount = 0;
        for (Todo todo : todos) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(todo.getCreatedAtTimestamp().getTime());
            int todoMonth = calendar.get(Calendar.MONTH) + 1;
            if ((todoMonth == month) && (todo.getDone())) {
                todosCount++;
            }
        }
        return todosCount;
    }

    public static int getDoneTodosCount() {
        ArrayList<Todo> todos = Variables.ACTIVE_USER.getTodos();
        int todosCount = 0;
        for (Todo todo : todos) {
            if (todo.getDone()) {
                todosCount++;
            }
        }
        return todosCount;
    }

    public static int getTodosCount() {
        ArrayList<Todo> todos = Variables.ACTIVE_USER.getTodos();
        return todos.size();
    }

    public static int getDeletedTodosCount() {
        ArrayList<Todo> todos = Variables.ACTIVE_USER.getTodos();
        int todosCount = 0;
        for (Todo todo : todos) {
            if (todo.getDeleted()) {
                todosCount++;
            }
        }
        return todosCount;
    }

    public static List<Todo> getClosestDeadlines(ArrayList<Todo> todos) {
        LocalDate currentDate = LocalDate.now();

        // Формат даты задания
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        List<Todo> sortedTodos = todos.stream()
                .filter(todo -> todo.getDeadline() != null)
                .sorted(Comparator.comparing(todo -> {
                    LocalDate deadline = LocalDate.parse(todo.getDeadline().replaceAll("[{}text]", ""), formatter);
                    return Math.abs(ChronoUnit.DAYS.between(deadline, currentDate));
                }))
                .limit(15)
                .collect(Collectors.toList());

        return sortedTodos;
    }


}
