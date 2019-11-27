package sample;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.sql.*;

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

        try {
            // Establish a connection to the database and flag the DBManager as connected.
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:./res/Hotel_Database");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

      int[] monthlyBookingCountArray = new int[12];
      double[] monthlyChargeArray = new double[12];
      for(int i = 0; i < 12; i++){
          int count = 0;
          ps = conn.prepareStatement(
                  "SELECT * FROM BOOKINGS WHERE MONTH(START_DATE) = '" + (i+1)+ "' AND " +
                          "YEAR(CURDATE())");
          rs = ps.executeQuery();
          while(rs.next()){
              count++;
          }
          monthlyBookingCountArray[i] = count;
          series1.getData().add(new XYChart.Data(months[i], monthlyBookingCountArray[i]));
      }
      for(int i = 0; i < 12; i++){
          double totalMonthlyRevenue = 0;
          ps = conn.prepareStatement(
                  "SELECT * FROM BOOKINGS WHERE MONTH(START_DATE) = '" + (i+1)+ "' AND " +
                          "YEAR(CURDATE())");
          rs = ps.executeQuery();
          while(rs.next()){
              totalMonthlyRevenue = totalMonthlyRevenue + rs.getDouble("TOTAL_CHARGE");
          }
          monthlyChargeArray[i] = totalMonthlyRevenue;
          series2.getData().add(new XYChart.Data(months[i], monthlyChargeArray[i]));
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
