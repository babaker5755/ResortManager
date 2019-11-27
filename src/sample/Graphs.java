package sample;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Graphs {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    final static String[] months = {"January","February","March","April","May","June","July",
        "August","September","October","November","December"};

  public void start() {
    try{
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

      XYChart.Series series2 = new XYChart.Series();
      series2.setName("Revenue");

      int[] monthlyBookingCountArr = new int[12];
      for(int i = 0; i < 12; i++){
          ps = conn.prepareStatement(
                  "SELECT COUNT(*) AS bookingCount FROM BOOKINGS WHERE MONTH(ARR_DATE) = "+ i +" AND " +
                          "YEAR(getdate())");
          rs = ps.executeQuery();
          monthlyBookingCountArr[i] = rs.getInt("bookingCount");
          series1.getData().add(new XYChart.Data(months[i], monthlyBookingCountArr[i]));

          ps = conn.prepareStatement(
                  "SELECT COUNT(*) AS bookingCount FROM BOOKINGS WHERE MONTH(ARR_DATE) = "+ i +" AND " +
                          "YEAR(getdate())");
          rs = ps.executeQuery();

      }

//      XYChart.Series series2 = new XYChart.Series();
//        series2.setName("Revenue");
//      series2.getData().add(new XYChart.Data(january, 25601.34));
//      series2.getData().add(new XYChart.Data(february, 20148.82));
//      series2.getData().add(new XYChart.Data(march, 10000));
//      series2.getData().add(new XYChart.Data(april, 35407.15));
//      series2.getData().add(new XYChart.Data(may, 12000));
//      series2.getData().add(new XYChart.Data(june, 25601.34));
//      series2.getData().add(new XYChart.Data(july, 20148.82));
//      series2.getData().add(new XYChart.Data(august, 10000));
//      series2.getData().add(new XYChart.Data(september, 35407.15));
//      series2.getData().add(new XYChart.Data(october, 12000));
//      series2.getData().add(new XYChart.Data(november, 25601.34));
//      series2.getData().add(new XYChart.Data(december, 20148.82));

      Scene scene = new Scene(bc, 800, 600);
      bc.getData().addAll(series1, series2);
      stage.setScene(scene);
      stage.show();
    }catch(SQLException e){
      e.printStackTrace();
    }
  }
}
