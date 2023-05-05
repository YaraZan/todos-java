package com.example.demo;

import com.example.demo.DbFunctions.TodosDAO;
import com.example.demo.DbFunctions.Variables;
import com.example.demo.Helpers.CalculativeFunctions;
import com.example.demo.Models.Router;
import com.example.demo.Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.converter.LocalDateStringConverter;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.function.UnaryOperator;

public class DashboardController {

    @FXML
    private Hyperlink RecentPageLink;

    @FXML
    private Hyperlink donePageLink;

    @FXML
    private Hyperlink todosPageLink;

    @FXML
    private Hyperlink logOut;

    @FXML
    private AnchorPane rootContainer;

    @FXML
    private Label userEmail;

    @FXML
    private VBox userInfoContainer;

    @FXML
    private Label userName;

    @FXML
    private VBox viewsContainer;

    @FXML
    private Hyperlink dashboardPageLink;

    @FXML
    private AnchorPane appBackground;

    @FXML
    void initialize() {
        screenAlignment();
        userName.setText("");
        userEmail.setText("");
        appBackground.setStyle("-fx-background-color: linear-gradient(to bottom, #23897D, transparent);\n");

        User user = Variables.ACTIVE_USER;
        userName.setText(user.getLogin());
        userEmail.setText(user.getEmail());
        System.out.println(user.getLogin());
        System.out.println(user.getEmail());

        logOut.setOnAction(e -> {
            try {
                new SceneController().switchScreen(e,"views/Reg-screen.fxml");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Router router = new Router(rootContainer);
        router.loadFirstely();

        todosPageLink.setOnAction(e -> {
            Router.changePage("Todos");
        });
        RecentPageLink.setOnAction(e -> {
            Router.changePage("Recent");
        });
        donePageLink.setOnAction(e -> {
            Router.changePage("Done");
        });
        dashboardPageLink.setOnAction(e -> {
            Router.changePage("Dashboard");
        });
    }


    private void screenAlignment() {
        viewsContainer.setSpacing(30);
        userInfoContainer.setSpacing(10);
        userInfoContainer.setPadding(new Insets(10));
        userInfoContainer.setAlignment(Pos.CENTER);
    }

}



