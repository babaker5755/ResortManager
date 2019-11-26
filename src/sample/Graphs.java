package sample;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Graphs {
    final static String january = "January";
    final static String february = "February";
    final static String march = "March";
    final static String april = "April";
    final static String may = "May";
    final static String june = "June";
    final static String july = "July";
    final static String august = "August";
    final static String september = "September";
    final static String october = "October";
    final static String november = "November";
    final static String december = "December";

  public void start() {
      Stage stage = new Stage();
    stage.setTitle("Bar Chart Sample");
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
    bc.setTitle("Monthly Summary Report");
    xAxis.setLabel("Month");
    yAxis.setLabel("Value");

    XYChart.Series series1 = new XYChart.Series();
    series1.setName("Bookings");
    series1.getData().add(new XYChart.Data(january, 25601.34));
    series1.getData().add(new XYChart.Data(february, 20148.82));
    series1.getData().add(new XYChart.Data(march, 10000));
    series1.getData().add(new XYChart.Data(april, 35407.15));
    series1.getData().add(new XYChart.Data(may, 12000));
    series1.getData().add(new XYChart.Data(june, 25601.34));
    series1.getData().add(new XYChart.Data(july, 20148.82));
    series1.getData().add(new XYChart.Data(august, 10000));
    series1.getData().add(new XYChart.Data(september, 35407.15));
    series1.getData().add(new XYChart.Data(october, 12000));
    series1.getData().add(new XYChart.Data(november, 25601.34));
    series1.getData().add(new XYChart.Data(december, 20148.82));

    XYChart.Series series2 = new XYChart.Series();
    series2.setName("Revenue");
    series2.getData().add(new XYChart.Data(january, 25601.34));
    series2.getData().add(new XYChart.Data(february, 20148.82));
    series2.getData().add(new XYChart.Data(march, 10000));
    series2.getData().add(new XYChart.Data(april, 35407.15));
    series2.getData().add(new XYChart.Data(may, 12000));
    series2.getData().add(new XYChart.Data(june, 25601.34));
    series2.getData().add(new XYChart.Data(july, 20148.82));
    series2.getData().add(new XYChart.Data(august, 10000));
    series2.getData().add(new XYChart.Data(september, 35407.15));
    series2.getData().add(new XYChart.Data(october, 12000));
    series2.getData().add(new XYChart.Data(november, 25601.34));
    series2.getData().add(new XYChart.Data(december, 20148.82));
    Scene scene = new Scene(bc, 800, 600);
    bc.getData().addAll(series1, series2);
    stage.setScene(scene);
    stage.show();
        }
}
