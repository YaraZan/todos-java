package com.example.demo.Models;

import com.example.demo.DbFunctions.Request;
import com.example.demo.DbFunctions.Variables;
import com.example.demo.Helpers.Page;
import com.example.demo.components.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.sql.SQLException;
import java.util.Objects;

public class Router {

    public static Page CURRENT_PAGE;

    public static VBox APP_CONTAINER;

    public Router(AnchorPane root) {
        APP_CONTAINER = (VBox) root.lookup("#viewsContainer");
    }

    public static void changePage(String pageName) {

        APP_CONTAINER.getChildren().removeAll(CURRENT_PAGE.getPageHeader(), CURRENT_PAGE.getPageBody());

        if (Objects.equals(pageName, "Todos")) {

            CURRENT_PAGE = new Page(HeaderTodos.todosHeader(), Todos.todosPage());

        } else if (Objects.equals(pageName, "Recent")) {

            CURRENT_PAGE = new Page(HeaderRecent.recentHeader(), Recent.recentPage());

        } else if (Objects.equals(pageName, "Done")) {

            CURRENT_PAGE = new Page(HeaderDone.doneHeader(), Done.donePage());

        } else if (Objects.equals(pageName, "Dashboard")) {

            CURRENT_PAGE = new Page(HeaderDashboard.dashboardHeader(), Dashboard.dashboardPage());

        }

        APP_CONTAINER.getChildren().addAll(CURRENT_PAGE.getPageHeader(), CURRENT_PAGE.getPageBody());
    }

    public static void loadFirstely() {
        CURRENT_PAGE = new Page(HeaderTodos.todosHeader(), Todos.todosPage());
        APP_CONTAINER.getChildren().addAll(CURRENT_PAGE.getPageHeader(), CURRENT_PAGE.getPageBody());
    }


}
