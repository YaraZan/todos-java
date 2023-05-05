package com.example.demo;

import com.example.demo.DbFunctions.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AuthController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField authLogin;

    @FXML
    private PasswordField authPassword;

    @FXML
    private Button signIn;

    @FXML
    private Hyperlink switchReg;

    @FXML
    private Label validationHint;


    @FXML
    void initialize() {
        switchReg.setOnAction(e -> {
            try {
                new SceneController().switchScreen(e,"views/Reg-screen.fxml");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        signIn.setOnAction(e -> {
            authorize(e);
        });
    }


    public void authorize(ActionEvent actionEvent) {

        if (Request.authorizeUser(authLogin.getText(), authPassword.getText())) {
            try {
                new SceneController().switchScreen(actionEvent,"views/Dashboard.fxml");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            System.out.println("error");
        }
    }


}