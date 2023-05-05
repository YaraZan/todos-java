package com.example.demo.Models;

import com.example.demo.DbFunctions.TodosDAO;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.CycleMethod;
import javafx.util.Duration;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import java.util.Objects;

public class LineChartModel {

    @FXML
    void initialize() {  }

    public LineChartModel() {

    }

    public static Node getLineChart() {

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis(0, 150, 10);

        xAxis.setLabel("Месяцы");
        yAxis.setLabel("Количество todo");

        final javafx.scene.chart.LineChart<String, Number> lineChart = new javafx.scene.chart.LineChart<String, Number>(xAxis, yAxis);

        XYChart.Series<String, Number> createdSeries = new XYChart.Series<>();
        createdSeries.setName("Созданные todo");
        createdSeries.getData().add(new XYChart.Data<>("Январь", TodosDAO.getTodoCountForMonth(1)));
        createdSeries.getData().add(new XYChart.Data<>("Февраль", TodosDAO.getTodoCountForMonth(2)));
        createdSeries.getData().add(new XYChart.Data<>("Март", TodosDAO.getTodoCountForMonth(3)));
        createdSeries.getData().add(new XYChart.Data<>("Апрель", TodosDAO.getTodoCountForMonth(4)));
        createdSeries.getData().add(new XYChart.Data<>("Май", TodosDAO.getTodoCountForMonth(5)));
        createdSeries.getData().add(new XYChart.Data<>("Июнь", TodosDAO.getTodoCountForMonth(6)));
        createdSeries.getData().add(new XYChart.Data<>("Июль", TodosDAO.getTodoCountForMonth(7)));
        createdSeries.getData().add(new XYChart.Data<>("Август", TodosDAO.getTodoCountForMonth(8)));
        createdSeries.getData().add(new XYChart.Data<>("Сентябрь", TodosDAO.getTodoCountForMonth(9)));
        createdSeries.getData().add(new XYChart.Data<>("Октябрь", TodosDAO.getTodoCountForMonth(10)));
        createdSeries.getData().add(new XYChart.Data<>("Ноябрь", TodosDAO.getTodoCountForMonth(11)));
        createdSeries.getData().add(new XYChart.Data<>("Декабрь", TodosDAO.getTodoCountForMonth(12)));

        XYChart.Series<String, Number> completedSeries = new XYChart.Series<String, Number>();
        completedSeries.setName("Выполненные todo");
        completedSeries.getData().add(new XYChart.Data<String, Number>("Январь", TodosDAO.getDoneTodoCountForMonth(1)));
        completedSeries.getData().add(new XYChart.Data<String, Number>("Февраль", TodosDAO.getDoneTodoCountForMonth(2)));
        completedSeries.getData().add(new XYChart.Data<String, Number>("Март", TodosDAO.getDoneTodoCountForMonth(3)));
        completedSeries.getData().add(new XYChart.Data<String, Number>("Апрель", TodosDAO.getDoneTodoCountForMonth(4)));
        completedSeries.getData().add(new XYChart.Data<String, Number>("Май", TodosDAO.getDoneTodoCountForMonth(5)));
        completedSeries.getData().add(new XYChart.Data<String, Number>("Июнь", TodosDAO.getDoneTodoCountForMonth(6)));
        completedSeries.getData().add(new XYChart.Data<String, Number>("Июль", TodosDAO.getDoneTodoCountForMonth(7)));
        completedSeries.getData().add(new XYChart.Data<String, Number>("Август", TodosDAO.getDoneTodoCountForMonth(8)));
        completedSeries.getData().add(new XYChart.Data<String, Number>("Сентябрь", TodosDAO.getDoneTodoCountForMonth(9)));
        completedSeries.getData().add(new XYChart.Data<String, Number>("Октябрь", TodosDAO.getDoneTodoCountForMonth(10)));
        completedSeries.getData().add(new XYChart.Data<String, Number>("Ноябрь", TodosDAO.getDoneTodoCountForMonth(11)));
        completedSeries.getData().add(new XYChart.Data<String, Number>("Декабрь", TodosDAO.getDoneTodoCountForMonth(12)));

        Bloom bloom = new Bloom();

        bloom.setThreshold(0.5);

        lineChart.setEffect(bloom);

        lineChart.getData().addAll(createdSeries, completedSeries);

        return lineChart;
    }

}
