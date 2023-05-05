package com.example.demo.components;

import com.example.demo.DbFunctions.TodosDAO;
import com.example.demo.Helpers.CalculativeFunctions;
import com.example.demo.Models.Router;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.converter.LocalDateStringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;

public class HeaderTodos {

    @FXML
    void initialize() {};

    public static Node todosHeader() {
        HBox todosHeader = new HBox();
        todosHeader.setSpacing(40);
        todosHeader.setAlignment(Pos.CENTER_LEFT);
        Label todosTitle = new Label("My Todos");
        Button addTodoBtn = new Button("New");
        addTodoBtn.setPrefWidth(160);
        addTodoBtn.setPrefHeight(40);

        todosTitle.setStyle("-fx-text-fill: #B0E4DD; -fx-font-size: 35; -fx-font-weight: 800");
        addTodoBtn.setStyle("-fx-background-radius: 15; -fx-background-color:  #3D4252; -fx-text-fill: #FFFFFF;");

        todosHeader.getChildren().addAll(todosTitle, addTodoBtn);

        addTodoBtn.setOnAction(e -> {
            Router.APP_CONTAINER.getChildren().remove(Router.CURRENT_PAGE.getPageBody());
            try {
                createForm();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        return todosHeader;
    }

    public static Node createForm() throws IOException {
        // Создаем VBox для размещения полей ввода и кнопок
        VBox form = new VBox();
        form.setSpacing(50); // установка расстояния между элементами
        form.setPadding(new Insets(40));
        form.setAlignment(Pos.TOP_LEFT);
        form.setPrefHeight(540);

        // Создаем текстовые поля
        TextField nameField = new TextField();
        TextField descriptionField = new TextField();
        TextField deadlineField = new TextField();
        // Валидация ввода даты дедлайна
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateStringConverter converter = new LocalDateStringConverter(dateFormatter, null);
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[0-9\\-]{0,10}")) {
                return change;
            }
            return null;
        };
        TextFormatter<LocalDate> formatter = new TextFormatter<>(converter, null, filter);
        formatter.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null) {
                deadlineField.setText("");
            } else {
                deadlineField.setText(dateFormatter.format(newValue));
            }
        });
        deadlineField.setTextFormatter(formatter);


        Label titleLabel = new Label("New Todo");
        Label deadlineLabel = new Label("Deadline");

        nameField.promptTextProperty().set("Name");
        descriptionField.promptTextProperty().set("Short description");
        deadlineField.promptTextProperty().set("DD-MM-YYYY");

        nameField.setPrefWidth(300);
        descriptionField.setPrefWidth(300);

        AnchorPane textFieldWrapper = new AnchorPane();
        VBox textWrp = new VBox();
        textWrp.setSpacing(10);

        textWrp.getChildren().addAll(nameField, descriptionField);
        textFieldWrapper.getChildren().addAll(textWrp);


        deadlineLabel.setStyle("-fx-text-fill: #009883");
        titleLabel.setStyle("-fx-text-fill: #545456; -fx-font-size: 20px");
        form.setStyle("-fx-background-color: #2E313C; -fx-background-radius: 10px;");
        nameField.setStyle("-fx-text-fill: #009883; -fx-background-radius: 10px; -fx-background-color: #3D4252");
        descriptionField.setStyle("-fx-text-fill: #009883; -fx-background-radius: 10px; -fx-background-color: #3D4252");
        deadlineField.setStyle("-fx-text-fill: #009883; -fx-background-radius: 10px; -fx-background-color: #3D4252");

        HBox deadlineWrapper = new HBox();
        deadlineWrapper.setSpacing(10);
        deadlineWrapper.setAlignment(Pos.CENTER_LEFT);
        deadlineWrapper.getChildren().addAll(deadlineLabel, deadlineField);

        HBox buttonWrapper = new HBox();
        Button cancelButton = new Button("Cancel");
        Button createButton = new Button("Сreate");
        // Обработчик событий на кнопку Create - создание todo
        createButton.setOnAction(e -> {
            TodosDAO.addTodo(nameField.getText(), descriptionField.getText(), deadlineField.getText(), new Timestamp(System.currentTimeMillis()));
            Router.APP_CONTAINER.getChildren().remove(form);

            Router.changePage("Todos");

        });
        // Обработчик событий на кнопку Cancel
        cancelButton.setOnAction(event -> {
            Router.APP_CONTAINER.getChildren().remove(form);

            Router.changePage("Todos");
        });

        cancelButton.setPrefWidth(100);
        createButton.setPrefWidth(100);

        buttonWrapper.setAlignment(Pos.BASELINE_RIGHT);
        buttonWrapper.setSpacing(10);
        buttonWrapper.getChildren().addAll(cancelButton, createButton);

        cancelButton.setStyle("-fx-background-radius: 10px; -fx-background-color: #B0E4DD; -fx-text-fill: #00A991");
        createButton.setStyle("-fx-background-radius: 10px; -fx-background-color: #00A991; -fx-text-fill: #FFFFFF");

        nameField.idProperty().set("nameField");
        descriptionField.idProperty().set("descriptionField");
        deadlineField.idProperty().set("deadlineField");

        form.getChildren().addAll(titleLabel, createTemplates() ,textFieldWrapper, deadlineWrapper,buttonWrapper);


        // Добавляем форму в контейнер
        Router.APP_CONTAINER.getChildren().add(form);

        return form;
    }

    public static Node createTemplates() {
        HBox templatesCont = new HBox();
        templatesCont.setSpacing(5);

        Pane templateHint = new Pane();
        Pane templateHealth = new Pane();
        Pane templateStudying = new Pane();
        templateHint.setStyle("-fx-background-color: none; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-border-color: #3D4252");
        templateHealth.setStyle("-fx-background-color: #00A991; -fx-background-radius: 15px;");
        templateStudying.setStyle("-fx-background-color: #B0E4DD; -fx-background-radius: 15px;");

        templateHealth.setOnMouseClicked(e -> {
            TextField name = (TextField) Router.APP_CONTAINER.lookup("#nameField");
            TextField descr = (TextField) Router.APP_CONTAINER.lookup("#descriptionField");
            TextField deadline = (TextField) Router.APP_CONTAINER.lookup("#deadlineField");

            name.setText("Стать здоровым!");
            descr.setText("Я обязательно должен выздороветь за эту неделю, так как скоро экзамены");
            deadline.setText(CalculativeFunctions.getNeedableDate("week"));
        });

        templateStudying.setOnMouseClicked(e -> {
            TextField name = (TextField) Router.APP_CONTAINER.lookup("#nameField");
            TextField descr = (TextField) Router.APP_CONTAINER.lookup("#descriptionField");
            TextField deadline = (TextField) Router.APP_CONTAINER.lookup("#deadlineField");


            name.setText("Выучить Java!");
            descr.setText("Я должен выучить язык java, поскольку он востребованный и удобный");
            deadline.setText(CalculativeFunctions.getNeedableDate("year"));

        });

        VBox hint = new VBox();
        VBox health = new VBox();
        VBox studying = new VBox();

        hint.setPadding(new Insets(15));
        health.setPadding(new Insets(15));
        studying.setPadding(new Insets(15));

        hint.setSpacing(30);
        health.setSpacing(30);
        studying.setSpacing(30);

        hint.setAlignment(Pos.BASELINE_CENTER);
        health.setAlignment(Pos.CENTER_LEFT);
        studying.setAlignment(Pos.CENTER_LEFT);

        Label hintTitle = new Label("Template");
        Label healthTitle = new Label("Задача: Здоровье");
        Label studyingTitle = new Label("Задача: учёба");
        hintTitle.setStyle("-fx-text-fill: #848484");
        healthTitle.setStyle("-fx-text-fill: #FFFFFF");
        studyingTitle.setStyle("-fx-text-fill: #000000");


        Label healthDeadline = new Label("Срок: неделя");
        Label studyingDeadline = new Label("Срок: год");
        healthDeadline.setStyle("-fx-text-fill: #2E313B");
        studyingDeadline.setStyle("-fx-text-fill: #2E313B");

        hint.getChildren().add(hintTitle);
        templateHint.getChildren().add(hint);

        health.getChildren().addAll(healthTitle, healthDeadline);
        templateHealth.getChildren().add(health);

        studying.getChildren().addAll(studyingTitle, studyingDeadline);
        templateStudying.getChildren().add(studying);

        templatesCont.getChildren().addAll(templateHint, templateHealth, templateStudying);

        return templatesCont;
    }

}
