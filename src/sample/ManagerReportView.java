package sample;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ManagerReportView extends Pane {
  private EventHandler<ActionEvent> logoutHandler;
  private JFXButton logoutBtn = new JFXButton("Logout");

  public void setLogoutHandler(EventHandler<ActionEvent> logoutHandler) {
    logoutBtn.setOnAction(logoutHandler);
    this.logoutHandler = logoutHandler;
  }

  public ManagerReportView() {
    // Add Label
    Label titleLabel = new Label("Generate Report");
    titleLabel.setPrefWidth(750);
    titleLabel.setLayoutY(15);
    titleLabel.setAlignment(Pos.CENTER);
    this.getChildren().add(titleLabel);

    // Add Label
    Label startLabel = new Label("Start Date");
    startLabel.setPrefWidth(250);
    startLabel.setLayoutX(50);
    startLabel.setLayoutY(50);
    this.getChildren().add(startLabel);

    // Add Date Picker
    JFXDatePicker startDatePicker = new JFXDatePicker();
    startDatePicker.setPrefWidth(250);
    startDatePicker.setLayoutX(50);
    startDatePicker.setLayoutY(70);
    this.getChildren().add(startDatePicker);
    startDatePicker.setPromptText("Ex: 01/01/1999");

    // Add Label
    Label endLabel = new Label("End Date");
    endLabel.setPrefWidth(250);
    endLabel.setLayoutX(450);
    endLabel.setLayoutY(50);
    this.getChildren().add(endLabel);

    // Add Date Picker
    JFXDatePicker endDatePicker = new JFXDatePicker();
    endDatePicker.setPrefWidth(250);
    endDatePicker.setLayoutX(450);
    endDatePicker.setLayoutY(70);
    this.getChildren().add(endDatePicker);
    endDatePicker.setPromptText("Ex: 01/01/2000");

    // Generate Report Button In Manager Tab
    JFXButton btn = new JFXButton("Generate Report");
    btn.getStyleClass().add("button-raised");
    btn.setPrefSize(150, 40);
    btn.setLayoutX(200);
    btn.setLayoutY(140);
    btn.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent e) {
            if(startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
              new Alert(Alert.AlertType.ERROR,"Please pick a start date and an end date!").showAndWait();
            }else{
              Date startDate = java.sql.Date.valueOf(startDatePicker.getValue());
              Date endDate = java.sql.Date.valueOf(endDatePicker.getValue());
              DRSRModalView drsrModalView = new DRSRModalView();
              drsrModalView.initialize(startDate, endDate);
            }
          }
        });
    this.getChildren().add(btn);

    JFXButton statisticsButton = new JFXButton("Resort Statistics");
    statisticsButton.getStyleClass().add("button-raised");
    statisticsButton.setPrefSize(150, 40);
    statisticsButton.setLayoutX(400);
    statisticsButton.setLayoutY(140);
    statisticsButton.setOnAction(
            new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent e) {
                Graphs barChart = new Graphs();
                barChart.start();
              }
            });
    this.getChildren().add(statisticsButton);

    // Log out currently signed in manager
    logoutBtn.getStyleClass().add("button-raised");
    logoutBtn.setPrefSize(150, 40);
    logoutBtn.setLayoutX(550);
    logoutBtn.setLayoutY(190);
    logoutBtn.setOnAction(logoutHandler);
    this.getChildren().add(logoutBtn);

  }
}
