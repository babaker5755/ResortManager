package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManagerReportView extends Pane {
  public  ManagerReportView() {
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
    DatePicker startDatePicker = new DatePicker();
    startDatePicker.setPrefWidth(250);
    startDatePicker.setLayoutX(50);
    startDatePicker.setLayoutY(70);
    this.getChildren().add(startDatePicker);

    // Add Label
    Label endLabel = new Label("End Date");
    endLabel.setPrefWidth(250);
    endLabel.setLayoutX(450);
    endLabel.setLayoutY(50);
    this.getChildren().add(endLabel);

    // Add Date Picker
    DatePicker endDatePicker = new DatePicker();
    endDatePicker.setPrefWidth(250);
    endDatePicker.setLayoutX(450);
    endDatePicker.setLayoutY(70);
    this.getChildren().add(endDatePicker);

    // Generate Report Button In Manager Tab
    Button btn = new Button("Generate Report");
    btn.setPrefSize(150, 40);
    btn.setLayoutX(300);
    btn.setLayoutY(140);
    btn.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent e) {
            DRSRModalView  drsrModalView = new DRSRModalView();
            drsrModalView.initialize();
          }
        });
    this.getChildren().add(btn);
  }
}
