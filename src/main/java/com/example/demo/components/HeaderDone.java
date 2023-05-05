package com.example.demo.components;

import com.example.demo.Models.Router;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class HeaderDone {

    @FXML
    void initialize() {};

    public static Node doneHeader() {
        HBox todosHeader = new HBox();
        todosHeader.setSpacing(40);
        todosHeader.setAlignment(Pos.CENTER_LEFT);
        Label todosTitle = new Label("Done");

        todosTitle.setStyle("-fx-text-fill: #B0E4DD; -fx-font-size: 35; -fx-font-weight: 800");

        todosHeader.getChildren().add(todosTitle);


        return todosHeader;
    }
}
