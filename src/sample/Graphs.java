package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.sql.*;

public class Graphs {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    final static String[] months = {"January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"};

    public void start() {
        try {
            final String[] comboList = {"Bookings", "Revenue"};
            final ComboBox combo_box = new ComboBox(FXCollections.observableArrayList(comboList));
            combo_box.getSelectionModel().selectFirst();
            GridPane gridPane = new GridPane();
            Stage stage = new Stage();
            stage.setTitle("Annual Summary Report");

            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis();

            final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
            bc.setTitle("Annual Summary Report");
            bc.setAnimated(false);
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
            for (int i = 0; i < 12; i++) {
                int count = 0;
                ps = conn.prepareStatement(
                        "SELECT * FROM BOOKINGS WHERE MONTH(START_DATE) = '" + (i + 1) + "' AND " +
                                "YEAR(CURDATE())");
                rs = ps.executeQuery();
                while (rs.next()) {
                    count++;
                }
                monthlyBookingCountArray[i] = count;
                series1.getData().add(new XYChart.Data(months[i], monthlyBookingCountArray[i]));
            }
            for (int i = 0; i < 12; i++) {
                double totalMonthlyRevenue = 0;
                ps = conn.prepareStatement(
                        "SELECT * FROM BOOKINGS WHERE MONTH(START_DATE) = '" + (i + 1) + "' AND " +
                                "YEAR(CURDATE())");
                rs = ps.executeQuery();
                while (rs.next()) {
                    totalMonthlyRevenue = totalMonthlyRevenue + rs.getDouble("TOTAL_CHARGE");
                }
                monthlyChargeArray[i] = totalMonthlyRevenue;
                series2.getData().add(new XYChart.Data(months[i], monthlyChargeArray[i]));
            }
            bc.getData().add(series1);
            Scene scene = new Scene(gridPane, 800, 450);
            ColumnConstraints column0 = new ColumnConstraints(100, 700, Double.MAX_VALUE);
            column0.setHgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().add(column0);
            gridPane.add(combo_box, 1, 0);
            gridPane.add(bc, 0, 1);
            gridPane.setHgap(10);
            stage.setScene(scene);
            stage.show();
            combo_box.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    boolean selectionClicked = true;
                    while (selectionClicked) {
                        if (combo_box.getSelectionModel().getSelectedItem().equals("Bookings")) {
                            bc.getData().removeAll(series1, series2);
                            bc.getData().addAll(series1);
                            selectionClicked = false;
                        } else if (combo_box.getSelectionModel().getSelectedItem().equals("Revenue")) {
                            bc.getData().removeAll(series1, series2);
                            bc.getData().addAll(series2);
                            selectionClicked = false;
                        }
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
