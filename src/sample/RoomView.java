package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.Date;

public class RoomView {

  Room room;
  BookingManager bookingList;

  public void setRoom(Room room) {
    this.room = room;
  }

  public void setBookingList(BookingManager bookingList) {
    this.bookingList = bookingList;
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
    priceLbl.setLayoutY(360);
    priceLbl.setAlignment(Pos.CENTER);
    pane.getChildren().add(priceLbl);

    Label nameLabel = new Label("Name");
    nameLabel.setLayoutX(25);
    nameLabel.setLayoutY(390);
    nameLabel.setAlignment(Pos.CENTER);
    pane.getChildren().add(nameLabel);

    TextField nameField = new TextField();
    nameField.setLayoutX(25);
    nameField.setLayoutY(420);
    nameField.setPrefWidth(175);
    nameField.setPrefHeight(40);
    pane.getChildren().add(nameField);

    Label addressLabel = new Label("Address");
    addressLabel.setLayoutX(25);
    addressLabel.setLayoutY(470);
    addressLabel.setAlignment(Pos.CENTER);
    pane.getChildren().add(addressLabel);

    TextField addressField = new TextField();
    addressField.setLayoutX(25);
    addressField.setLayoutY(500);
    addressField.setPrefWidth(175);
    addressField.setPrefHeight(40);
    pane.getChildren().add(addressField);

    Label emailLabel = new Label("Email Address");
    emailLabel.setLayoutX(205);
    emailLabel.setLayoutY(390);
    emailLabel.setAlignment(Pos.CENTER);
    pane.getChildren().add(emailLabel);

    TextField emailField = new TextField();
    emailField.setLayoutX(205);
    emailField.setLayoutY(420);
    emailField.setPrefWidth(175);
    emailField.setPrefHeight(40);
    pane.getChildren().add(emailField);

    Label ccLabel = new Label("Credit Card Number");
    ccLabel.setLayoutX(205);
    ccLabel.setLayoutY(470);
    ccLabel.setAlignment(Pos.CENTER);
    pane.getChildren().add(ccLabel);

    TextField creditCardField = new TextField();
    creditCardField.setLayoutX(205);
    creditCardField.setLayoutY(500);
    creditCardField.setPrefWidth(175);
    creditCardField.setPrefHeight(40);
    pane.getChildren().add(creditCardField);

    Label checkInLabel = new Label("Check-in Date");
    checkInLabel.setLayoutX(25);
    checkInLabel.setLayoutY(550);
    checkInLabel.setAlignment(Pos.CENTER);
    pane.getChildren().add(checkInLabel);

    DatePicker checkInPicker = new DatePicker();
    checkInPicker.setLayoutX(25);
    checkInPicker.setLayoutY(580);
    checkInPicker.setPrefWidth(175);
    checkInPicker.setPrefHeight(40);
    pane.getChildren().add(checkInPicker);

    Label checkOutLabel = new Label("Check-out Date");
    checkOutLabel.setLayoutX(205);
    checkOutLabel.setLayoutY(550);
    checkOutLabel.setAlignment(Pos.CENTER);
    pane.getChildren().add(checkOutLabel);

    DatePicker checkOutPicker = new DatePicker();
    checkOutPicker.setLayoutX(205);
    checkOutPicker.setLayoutY(580);
    checkOutPicker.setPrefWidth(175);
    checkOutPicker.setPrefHeight(40);
    pane.getChildren().add(checkOutPicker);

    // Add Button
    Button btn = new Button("Submit");
    btn.setPrefSize(200, 50);
    btn.setLayoutX(100);
    btn.setLayoutY(640);
    btn.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent e) {
            // Do something
            System.out.println("Submit Button Pressed" + priceLbl.toString());
            System.out.print(room.getRoomNumber() + "\n\n\n");

            Stage confirmPopup = new Stage();
            confirmPopup.initModality(Modality.APPLICATION_MODAL);
            VBox popupVbox = new VBox();
            Pane pane = new Pane();
            Label lbl = new Label("You're booked! Your confirmation number is:"); // TODO: Add confirmation
            pane.getChildren().add(lbl);
            Scene dialogScene = new Scene(popupVbox, 250, 50); //Change confirmation popup size here
            popupVbox.getChildren().add(pane);
            confirmPopup.setScene(dialogScene);
            confirmPopup.show();

            Booking newBooking =
                new Booking(
                    "98328923",
                    room.getRoomNumber(),
                    room.getPrice(),
                    nameField.getText(),
                    addressField.getText(),
                    creditCardField.getText(),
                    emailField.getText(),
                    java.sql.Date.valueOf(checkInPicker.getValue()),
                    java.sql.Date.valueOf(checkOutPicker.getValue()));
            bookingList.addBooking(newBooking);
            room.setVacant(false);
            DatabaseManager DBM = new DatabaseManager();
            // adding bookings to database
            DBM.addBookingsToDB(bookingList.getBookingList());

            for (Booking b : bookingList.getBookingList()) {
              System.out.print("Room #: " + b.getRoomNumber() + "\n");
              System.out.print("Check-in Date: " + b.getCheckInDate() + "\n");
              System.out.print("Check-out Date: " + b.getCheckOutDate() + "\n");
            }
            System.out.print("\n\n");
          }
        });
    pane.getChildren().add(btn);

    dialogVbox.getChildren().add(pane);
    Scene dialogScene = new Scene(dialogVbox, 400, 700);
    dialog.setScene(dialogScene);
    dialog.show();
  }
}
