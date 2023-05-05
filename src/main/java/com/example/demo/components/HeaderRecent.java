package com.example.demo.components;

import com.example.demo.Models.Router;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class HeaderRecent {
    @FXML
    void initialize() {};

    public static Node recentHeader() {
        HBox recentHeader = new HBox();
        recentHeader.setSpacing(40);
        recentHeader.setAlignment(Pos.CENTER_LEFT);
        Label todosTitle = new Label("Recent");

        todosTitle.setStyle("-fx-text-fill: #B0E4DD; -fx-font-size: 35; -fx-font-weight: 800");

        recentHeader.getChildren().add(todosTitle);

        return recentHeader;
    }
}
