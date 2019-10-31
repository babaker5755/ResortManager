package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManagerRoomView {

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
    imgView.setFitHeight(135);
    imgView.setFitWidth(135);
    imgView.setLayoutX(130);
    imgView.setLayoutY(75);
    pane.getChildren().add(imgView);

    // Add Label
    Label priceLbl = new Label("$" + room.getPrice());
    priceLbl.setPrefWidth(400);
    priceLbl.setLayoutX(0);
    priceLbl.setLayoutY(235);
    priceLbl.setAlignment(Pos.CENTER);
    pane.getChildren().add(priceLbl);

    TableView tableView = new TableView();
    tableView.setPrefWidth(400);
    tableView.setPrefHeight(300);
    tableView.setLayoutX(0);
    tableView.setLayoutY(300);
    pane.getChildren().add(tableView);

    dialogVbox.getChildren().add(pane);
    Scene dialogScene = new Scene(dialogVbox, 400, 650);
    dialog.setScene(dialogScene);
    dialog.show();

    Button cancelBooking = new Button("Cancel Booking");
    cancelBooking.setPrefSize(150, 40);
    cancelBooking.setLayoutX(125);
    cancelBooking.setLayoutY(605);
    cancelBooking.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            VBox dialogVbox = new VBox();
            Pane pane = new Pane();
            Label confirmCancellation = new Label("Are sure you want cancel the booking? ");
            confirmCancellation.setLayoutX(75);
            confirmCancellation.setLayoutY(30);
            Button yesButton = new Button("Yes");
            yesButton.setPrefSize(50, 30);
            yesButton.setLayoutX(100);
            yesButton.setLayoutY(150);
            Button noButton = new Button("No");
            noButton.setLayoutX(200);
            noButton.setLayoutY(150);
            noButton.setPrefSize(50, 30);
            pane.getChildren().add(noButton);
            pane.getChildren().add(yesButton);

            pane.getChildren().add(confirmCancellation);
            Scene dialogScene = new Scene(dialogVbox, 350, 250);
            dialogVbox.getChildren().add(pane);
            dialog.setScene(dialogScene);
            dialog.show();
          }
        });
    pane.getChildren().add(cancelBooking);
  }
}
