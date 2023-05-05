package com.example.demo;

import com.example.demo.DbFunctions.Request;
import com.example.demo.DbFunctions.UserDAO;
import com.example.demo.DbFunctions.Variables;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

import static com.example.demo.DbFunctions.Request.registrateUser;

public class RegController {
    @FXML
    private PasswordField regConfirmPassword;

    @FXML
    private TextField regLogin;

    @FXML
    private PasswordField regPassword;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button signUp;

    @FXML
    private Hyperlink switchAuth;

    @FXML
    private TextField regEmail;

    @FXML
    private Label validationHint;

    @FXML
    void initialize() {
        switchAuth.setOnAction(e -> {
            try {
                new SceneController().switchScreen(e,"views/Auth-screen.fxml");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        signUp.setOnAction(e-> {
            setData(e);
        });
    }

    public void setData(ActionEvent actionEvent) {
        System.out.println(regLogin.getText());
        System.out.println(regEmail.getText());
        System.out.println(regPassword.getText());


        if (!((regEmail.getText()).contains("@"))) {
            validationHint.setText("");
            validationHint.setText("Введите корректный email!");
        } else if (!(Objects.equals(regPassword.getText(), regConfirmPassword.getText()))) {
            validationHint.setText("");
            validationHint.setText("Пароли не совпадают!");
        } else if (new UserDAO().userExists(regLogin.getText())) {
            validationHint.setText("");
            validationHint.setText("Имя занято!");
        } else if (new UserDAO().emailExists(regEmail.getText())) {
            validationHint.setText("");
            validationHint.setText("Почта уже зарегистрирована!");
        } else if ((regEmail.getText() == "") || (regLogin.getText() == "") || (regPassword.getText() == "") || (regConfirmPassword.getText() == ""))  {
            validationHint.setText("");
            validationHint.setText("Заполните все поля!");
        } else {
            validationHint.setText("");
            Request.registrateUser(regLogin.getText(), regEmail.getText(), regPassword.getText());

            if (Request.authorizeUser(Variables.ACTIVE_USER.getLogin(), Variables.ACTIVE_USER.getPassword())) {
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


}
