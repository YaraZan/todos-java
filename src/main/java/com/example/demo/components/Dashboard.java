package com.example.demo.components;

import com.example.demo.DbFunctions.Request;
import com.example.demo.DbFunctions.TodosDAO;
import com.example.demo.DbFunctions.Variables;
import com.example.demo.Models.LineChartModel;
import com.example.demo.Models.Router;
import com.example.demo.Models.Todo;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Dashboard {

    private static int METRIC_WIDTH = 185;
    private static int METRIC_HEIGHT = 141;

    private static final int CARD_WIDTH = 360;
    private static final int CARD_HEIGHT = 242;
    private static final int GAP = 10;
    private static final int PADDING = 20;

    @FXML
    void initialize() {  }

    public static Node dashboardPage() {

        HBox dashboardContainerHorizontal = new HBox();
        VBox dashboardContainerVertical = new VBox();

        VBox closestTodosWidget = new VBox();
        closestTodosWidget.setPrefHeight(600);
        closestTodosWidget.setPrefWidth(470);
        closestTodosWidget.setStyle("-fx-background-color: #1B1C20; -fx-background-radius: 15");
        Label closestTodosTitle = new Label("Closest todos");
        closestTodosTitle.setStyle("-fx-text-fill:  #00A991; -fx-font-weight: 800; -fx-font-size: 22");
        closestTodosWidget.setPadding(new Insets(25));

        ScrollPane scrollPane = new ScrollPane();
        FlowPane todosCont = new FlowPane(GAP, GAP);
        todosCont.setHgap(10);
        todosCont.setVgap(10);
        todosCont.setPrefWrapLength((CARD_WIDTH + GAP));
        todosCont.setPadding(new Insets(10));

        ArrayList<Todo> todArr = TodosDAO.getUncompletedTodos(Variables.ACTIVE_USER.getTodos());
        List<Todo> todoArrayList = TodosDAO.getClosestDeadlines(todArr);

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
                    Router.changePage("Dashboard");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });

            doneButton.setOnAction(e -> {
                TodosDAO.setDoneTodo(todo.getId());
                try {
                    Variables.ACTIVE_USER.setTodos(Request.getTodosForUser(Variables.ACTIVE_USER.getId()));
                    Router.changePage("Dashboard");
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

        closestTodosWidget.getChildren().addAll(closestTodosTitle,scrollPane);

        HBox metricsWrapper = new HBox();

        AnchorPane createdMetric = new AnchorPane();
        AnchorPane doneMetric = new AnchorPane();
        AnchorPane deletedMetric = new AnchorPane();

        Label createdMetricData = new Label(Integer.toString(TodosDAO.getTodosCount()));
        Label doneMetricData = new Label(Integer.toString(TodosDAO.getDoneTodosCount()));
        Label deletedMetricData = new Label(Integer.toString(TodosDAO.getDeletedTodosCount()));

        VBox diagramContainer = new VBox();
        diagramContainer.setPadding(new Insets(20));
        diagramContainer.setStyle("-fx-background-radius: 20; -fx-background-color: #2E313C");
        createdMetricData.setStyle("-fx-font-weight: 700;-fx-font-size: 22px;  -fx-text-fill: #B0E4DD;");
        doneMetricData.setStyle("-fx-font-weight: 700;-fx-font-size: 22px;  -fx-text-fill: #B0E4DD;");
        deletedMetricData.setStyle("-fx-font-weight: 700;-fx-font-size: 22px;  -fx-text-fill: #B0E4DD;");

        Node lineChart = LineChartModel.getLineChart();
        diagramContainer.getChildren().add(lineChart);

        dashboardContainerHorizontal.setPrefWidth(960);

        createdMetric.setPrefWidth(METRIC_WIDTH);
        createdMetric.setPrefHeight(METRIC_HEIGHT);
        doneMetric.setPrefWidth(METRIC_WIDTH);
        doneMetric.setPrefHeight(METRIC_HEIGHT);
        deletedMetric.setPrefWidth(METRIC_WIDTH);
        deletedMetric.setPrefHeight(METRIC_HEIGHT);
        createdMetric.idProperty().set("createdPane");
        doneMetric.idProperty().set("donePane");
        deletedMetric.idProperty().set("deletedPane");
        dashboardContainerHorizontal.setSpacing(25);
        dashboardContainerVertical.setSpacing(25);
        metricsWrapper.setSpacing(6);

        createdMetric.setPadding(new Insets(15));
        doneMetric.setPadding(new Insets(15));
        deletedMetric.setPadding(new Insets(15));

        createdMetric.setBottomAnchor(createdMetricData, 0.0);
        createdMetric.setRightAnchor(createdMetricData, 0.0);
        doneMetric.setBottomAnchor(doneMetricData, 0.0);
        doneMetric.setRightAnchor(doneMetricData, 0.0);
        deletedMetric.setBottomAnchor(deletedMetricData, 0.0);
        deletedMetric.setRightAnchor(deletedMetricData, 0.0);

        createdMetric.getChildren().add(createdMetricData);
        doneMetric.getChildren().add(doneMetricData);
        deletedMetric.getChildren().add(deletedMetricData);

        metricsWrapper.getChildren().addAll(createdMetric, doneMetric, deletedMetric);

        dashboardContainerVertical.getChildren().addAll(diagramContainer, metricsWrapper);

        dashboardContainerHorizontal.getChildren().addAll(dashboardContainerVertical, closestTodosWidget);

        return dashboardContainerHorizontal;
    }
}
