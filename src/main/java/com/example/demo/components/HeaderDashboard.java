package com.example.demo.components;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class HeaderDashboard {
    @FXML
    void initialize() {};

    public static Node dashboardHeader() {
        HBox dashboardHeader = new HBox();
        dashboardHeader.setSpacing(40);
        dashboardHeader.setAlignment(Pos.CENTER_LEFT);
        Label todosTitle = new Label("Dashboard");

        todosTitle.setStyle("-fx-text-fill: #B0E4DD; -fx-font-size: 35; -fx-font-weight: 800");

        dashboardHeader.getChildren().add(todosTitle);


        return dashboardHeader;
    }
}
