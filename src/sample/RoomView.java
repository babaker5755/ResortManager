package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RoomView {

  Room room;


  public void setRoom(Room room) {
    this.room = room;
  }

  public void presentRoomView() {
    final Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    VBox dialogVbox = new VBox(20);

    // Add elements to this pane
    Pane pane = new Pane();

    // Add Label
    Label bedSizeLbl = new Label(room.getBedSize());
    bedSizeLbl.setPrefWidth(400);
    bedSizeLbl.setLayoutX(0);
    bedSizeLbl.setLayoutY(15);
    bedSizeLbl.setAlignment(Pos.CENTER);
    pane.getChildren().add(bedSizeLbl);

    // Add Label
    Label numBedsLbl = new Label("Number of Beds: " + room.getNumBeds());
    numBedsLbl.setPrefWidth(400);
    numBedsLbl.setLayoutX(0);
    numBedsLbl.setLayoutY(40);
    numBedsLbl.setAlignment(Pos.CENTER);
    pane.getChildren().add(numBedsLbl);

    Image roomImage = new Image(Controller.class.getResourceAsStream("hotelRoom.jpg"));
    ImageView imgView = new ImageView(roomImage);
    imgView.setFitHeight(270);
    imgView.setFitWidth(270);
    imgView.setLayoutX(65);
    imgView.setLayoutY(75);
    pane.getChildren().add(imgView);

    // Add Label
    Label priceLbl = new Label("$" + room.getPrice());
    priceLbl.setPrefWidth(400);
    priceLbl.setLayoutX(0);
    priceLbl.setLayoutY(370);
    priceLbl.setAlignment(Pos.CENTER);
    pane.getChildren().add(priceLbl);


    Label emailLabel = new Label("Email Address");
    emailLabel.setLayoutX(25);
    emailLabel.setLayoutY(410);
    emailLabel.setAlignment(Pos.CENTER);
    pane.getChildren().add(emailLabel);

    TextField emailField = new TextField();
    emailField.setLayoutX(25);
    emailField.setLayoutY(440);
    emailField.setPrefWidth(350);
    emailField.setPrefHeight(40);
    pane.getChildren().add(emailField);

    Label ccLabel = new Label("Credit Card Number");
    ccLabel.setLayoutX(25);
    ccLabel.setLayoutY(490);
    ccLabel.setAlignment(Pos.CENTER);
    pane.getChildren().add(ccLabel);

    TextField creditCardField = new TextField();
    creditCardField.setLayoutX(25);
    creditCardField.setLayoutY(520);
    creditCardField.setPrefWidth(350);
    creditCardField.setPrefHeight(40);
    pane.getChildren().add(creditCardField);


    // Add Button
    Button btn = new Button("Submit");
    btn.setPrefSize(200, 50);
    btn.setLayoutX(100);
    btn.setLayoutY(590);
    btn.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent e) {
            // Do something
            System.out.println("Submit Button Pressed" + priceLbl.toString());
          }
        });
    pane.getChildren().add(btn);

    dialogVbox.getChildren().add(pane);
    Scene dialogScene = new Scene(dialogVbox, 400, 650);
    dialog.setScene(dialogScene);
    dialog.show();
  }
}
