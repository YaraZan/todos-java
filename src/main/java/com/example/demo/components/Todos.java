package com.example.demo.components;

import com.example.demo.DbFunctions.Request;
import com.example.demo.DbFunctions.TodosDAO;
import com.example.demo.DbFunctions.Variables;
import com.example.demo.Helpers.CalculativeFunctions;
import com.example.demo.Models.Router;
import com.example.demo.Models.Todo;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.sql.SQLException;
import java.util.ArrayList;

public class Todos {

    private static final int CARD_WIDTH = 360;
    private static final int CARD_HEIGHT = 242;
    private static final int GAP = 10;
    private static final int PADDING = 20;
    @FXML
    void initialize() {  }

    public static Node todosPage() {

        ScrollPane scrollPane = new ScrollPane();
        FlowPane todosCont = new FlowPane(GAP, GAP);
        todosCont.setHgap(10);
        todosCont.setVgap(10);
        todosCont.setPrefWrapLength((CARD_WIDTH + GAP) * 3);
        todosCont.setPadding(new Insets(10));

        ArrayList<Todo> todArr = Variables.ACTIVE_USER.getTodos();
        ArrayList<Todo> todoArrayList = TodosDAO.getUncompletedTodos(todArr);

        for (int i = 0; i < (todoArrayList).size(); i++) {
            Todo todo = new Todo(
                    (todoArrayList.get(i)).getId(),
                    (todoArrayList.get(i)).getUid(),
                    (todoArrayList.get(i)).getName(),
                    (todoArrayList.get(i)).getDescr(),
                    (todoArrayList.get(i)).getDeadline(),
                    (todoArrayList.get(i)).getDone(),
                    (todoArrayList.get(i)).getDeleted()
            );

            VBox todoCardCont = new VBox();
            todoCardCont.setPadding(new Insets(PADDING));
            todoCardCont.setSpacing(10);
            todoCardCont.setPrefWidth(CARD_WIDTH);
            todoCardCont.setPrefHeight(CARD_HEIGHT);

            Label name = new Label(todo.getName());
            Label description = new Label(todo.getDescr());
            Label deadlinesLabel = new Label("Deadlines");
            Label deadline = new Label(todo.getDeadline());
            Button deleteButton = new Button("Delete");
            Button doneButton = new Button("Done");

            name.setMaxWidth(311);
            description.setMaxWidth(311);
            description.setPrefHeight(100);
            description.setMaxHeight(100);
            deleteButton.setPrefWidth(200);
            doneButton.setPrefWidth(200);
            deleteButton.setPrefHeight(40);
            doneButton.setPrefHeight(40);

            deleteButton.setOnAction(e -> {
                TodosDAO.deleteTodo(todo.getId());
                try {
                    Variables.ACTIVE_USER.setTodos(Request.getTodosForUser(Variables.ACTIVE_USER.getId()));
                    Router.changePage("Todos");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });

            doneButton.setOnAction(e -> {
                TodosDAO.setDoneTodo(todo.getId());
                try {
                    Variables.ACTIVE_USER.setTodos(Request.getTodosForUser(Variables.ACTIVE_USER.getId()));
                    Router.changePage("Todos");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });

            HBox buttonWrapper = new HBox();
            buttonWrapper.setAlignment(Pos.BASELINE_LEFT);
            buttonWrapper.setSpacing(10);
            buttonWrapper.getChildren().addAll(deleteButton, doneButton);

            HBox deadlineWrapper = new HBox();
            deadlineWrapper.setSpacing(20);
            deadlineWrapper.getChildren().addAll(deadlinesLabel, deadline);

            todoCardCont.setStyle("-fx-background-color: #2E313C; -fx-background-radius: 15");
            name.setStyle("-fx-text-fill:  #E6F6F4; -fx-font-weight: 800; -fx-font-size: 30");
            description.setStyle("-fx-text-fill:  #848484; -fx-font-weight: 600; -fx-font-size: 18; -fx-wrap-text: true");
            deadlinesLabel.setStyle("-fx-text-fill: #009883; -fx-font-weight: 600; -fx-font-size: 18");
            deadline.setStyle("-fx-text-fill: #009883; -fx-font-weight: 600; -fx-font-size: 18");
            deleteButton.setStyle("-fx-text-fill: #009883; -fx-background-color: #B0E4DD; -fx-font-size: 18");
            doneButton.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: #009883; -fx-font-size: 18");

            todoCardCont.getChildren().addAll(name, description, deadlineWrapper, buttonWrapper);

            todosCont.getChildren().add(todoCardCont);

            scrollPane.setContent(todosCont);

        }

        return scrollPane;
    }
}
