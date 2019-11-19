package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RoomServicePane extends Pane {
  int numberOfItems = 1;

  Pane mainPane = new Pane();
  Pane menuPane = new Pane();

  public void presentRoomServicePane() {

    final Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    VBox dialogVbox = new VBox(20);

    // Add elements to this pane
    mainPane.setPrefHeight(1000);
    mainPane.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
    mainPane.getStyleClass().add("root");

    Label titleLabel = new Label("Room Service");
    titleLabel.getStyleClass().add("title-label");
    titleLabel.setLayoutX(0);
    titleLabel.setLayoutY(20);
    titleLabel.setPrefWidth(400);
    titleLabel.setAlignment(Pos.CENTER);
    mainPane.getChildren().add(titleLabel);

    this.menuPane = setupMenuPane();
    mainPane.getChildren().add(menuPane);

    Label roomNumberLabel = new Label("Room Number");
    roomNumberLabel.getStyleClass().add("label");
    roomNumberLabel.setLayoutX(25);
    roomNumberLabel.setLayoutY(400);
    roomNumberLabel.setAlignment(Pos.CENTER);
    mainPane.getChildren().add(roomNumberLabel);

    JFXTextField roomNumberField = new JFXTextField();
    roomNumberField.setLayoutX(25);
    roomNumberField.setLayoutY(410);
    roomNumberField.setPrefWidth(350);
    roomNumberField.setPrefHeight(40);
    mainPane.getChildren().add(roomNumberField);

    Label ccLabel = new Label("Credit Card Number");
    ccLabel.getStyleClass().add("label");
    ccLabel.setLayoutX(25);
    ccLabel.setLayoutY(480);
    ccLabel.setAlignment(Pos.CENTER);
    mainPane.getChildren().add(ccLabel);

    JFXTextField creditCardField = new JFXTextField();
    creditCardField.setLayoutX(25);
    creditCardField.setLayoutY(490);
    creditCardField.setPrefWidth(350);
    creditCardField.setPrefHeight(40);
    mainPane.getChildren().add(creditCardField);

    Label errorLabel = new Label("");
    errorLabel.getStyleClass().add("label");
    errorLabel.setPrefWidth(400);
    errorLabel.setLayoutX(0);
    errorLabel.setLayoutY(600);
    errorLabel.setAlignment(Pos.CENTER);
    mainPane.getChildren().add(errorLabel);

    // Add Button
    JFXButton btn = new JFXButton("Submit Order");
    btn.setPrefSize(200, 50);
    btn.getStyleClass().add("button-raised");
    btn.setLayoutX(100);
    btn.setLayoutY(630);
    btn.setOnAction(
            new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent e) {
                errorLabel.setText("Order Submitted");

              }
            });
    mainPane.getChildren().add(btn);

    dialogVbox.getChildren().add(mainPane);
    Scene dialogScene = new Scene(dialogVbox, 400, 700);
    dialog.setScene(dialogScene);
    dialog.show();
  }

  private Pane setupMenuPane() {
    Pane menuPane = new Pane();
    menuPane.setLayoutX(0);
    menuPane.setLayoutY(60);
    menuPane.setPrefHeight(350);
    menuPane.setPrefWidth(400);
    int baseY = 0;
    for (int i = 0; i < numberOfItems; i++) {
      baseY = i * 65;
      Label roomNumberLabel = new Label("Food Item");
      roomNumberLabel.getStyleClass().add("label");
      roomNumberLabel.setLayoutX(25);
      roomNumberLabel.setLayoutY(baseY);
      roomNumberLabel.setPrefHeight(15);
      roomNumberLabel.setAlignment(Pos.CENTER);
      menuPane.getChildren().add(roomNumberLabel);

      JFXComboBox comboBox = new JFXComboBox();
      comboBox.setLayoutY(baseY + 10);
      comboBox.setLayoutX(25);
      comboBox.setPrefWidth(350);
      comboBox.setPrefHeight(40);
      menuPane.getChildren().add(comboBox);
    }
    // Add Button
    JFXButton btn = new JFXButton("Add Food Item");
    btn.setPrefSize(150, 25);
    btn.getStyleClass().add("button-raised");
    btn.setLayoutX(25);
    btn.setLayoutY(baseY + 60);
    btn.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent e) {
            addMenuItem();
          }
        });
    menuPane.getChildren().add(btn);
    return menuPane;
  }

  private void addMenuItem() {
    setNumberOfItems(numberOfItems + 1);
    this.mainPane.getChildren().remove(this.menuPane);
    this.menuPane = setupMenuPane();
    this.mainPane.getChildren().add(this.menuPane);
  }

  private void setNumberOfItems(int numberOfItems) {
    if (numberOfItems < 5) {
      this.numberOfItems = numberOfItems;
    }
  }
}
