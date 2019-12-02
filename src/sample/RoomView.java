package sample;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

public class RoomView {

  Room room;
  BookingManager bookingList = new BookingManager();

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
    pane.setPrefHeight(1000);
    pane.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
    pane.getStyleClass().add("root");


    // Add Label
    Label nameLbl = new Label(room.getRoomName());
    nameLbl.getStyleClass().add("medium-label");
    nameLbl.setPrefWidth(400);
    nameLbl.setLayoutX(0);
    nameLbl.setLayoutY(15);
    nameLbl.setAlignment(Pos.CENTER);
    pane.getChildren().add(nameLbl);

    // Add Label
    Label numBedsLbl = new Label("Number of Beds: " + room.getNumBeds());
    numBedsLbl.getStyleClass().add("label");
    numBedsLbl.setPrefWidth(400);
    numBedsLbl.setLayoutX(0);
    numBedsLbl.setLayoutY(40);
    numBedsLbl.setAlignment(Pos.CENTER);
    pane.getChildren().add(numBedsLbl);

    Image roomImage = new Image(Controller.class.getResourceAsStream(room.getImageUrl()));
    ImageView imgView = new ImageView(roomImage);
    //Image spyRoomImage = new Image(Controller.class.getResourceAsStream("Room_Spy.jpg"));
    //ImageView imgView = new ImageView(spyRoomImage);

    imgView.setFitHeight(270);
    imgView.setFitWidth(270);
    imgView.setLayoutX(65);
    imgView.setLayoutY(75);
    pane.getChildren().add(imgView);

    // Add Label
    Label priceLbl = new Label("$" + room.getPrice());
    priceLbl.getStyleClass().add("label");
    priceLbl.setPrefWidth(400);
    priceLbl.setLayoutX(0);
    priceLbl.setLayoutY(360);
    priceLbl.setAlignment(Pos.CENTER);
    pane.getChildren().add(priceLbl);

    Label nameLabel = new Label("Name");
    nameLabel.getStyleClass().add("label");
    nameLabel.setLayoutX(25);
    nameLabel.setLayoutY(400);
    nameLabel.setAlignment(Pos.CENTER);
    pane.getChildren().add(nameLabel);

    JFXTextField nameField = new JFXTextField();
    nameField.setLayoutX(25);
    nameField.setLayoutY(410);
    nameField.setPrefWidth(175);
    nameField.setPrefHeight(40);
    pane.getChildren().add(nameField);
    nameField.setPromptText("Ex: John Doe");

    Label addressLabel = new Label("Address");
    addressLabel.getStyleClass().add("label");
    addressLabel.setLayoutX(25);
    addressLabel.setLayoutY(480);
    addressLabel.setAlignment(Pos.CENTER);
    pane.getChildren().add(addressLabel);

    JFXTextField addressField = new JFXTextField();
    addressField.setLayoutX(25);
    addressField.setLayoutY(490);
    addressField.setPrefWidth(175);
    addressField.setPrefHeight(40);
    pane.getChildren().add(addressField);
    addressField.setPromptText("Ex: 51 Pineapple Ave.");

    Label emailLabel = new Label("Email Address");
    emailLabel.getStyleClass().add("label");
    emailLabel.setLayoutX(205);
    emailLabel.setLayoutY(400);
    emailLabel.setAlignment(Pos.CENTER);
    pane.getChildren().add(emailLabel);

    JFXTextField emailField = new JFXTextField();
    emailField.setLayoutX(205);
    emailField.setLayoutY(410);
    emailField.setPrefWidth(175);
    emailField.setPrefHeight(40);
    pane.getChildren().add(emailField);
    emailField.setPromptText("Ex: email@email.com");

    Label ccLabel = new Label("Credit Card Number");
    ccLabel.getStyleClass().add("label");
    ccLabel.setLayoutX(205);
    ccLabel.setLayoutY(480);
    ccLabel.setAlignment(Pos.CENTER);
    pane.getChildren().add(ccLabel);

    JFXTextField creditCardField = new JFXTextField();
    creditCardField.setLayoutX(205);
    creditCardField.setLayoutY(490);
    creditCardField.setPrefWidth(175);
    creditCardField.setPrefHeight(40);
    pane.getChildren().add(creditCardField);
    creditCardField.setPromptText("Ex: 5555 5555 5555 5555");

    Label checkInLabel = new Label("Check-in Date");
    checkInLabel.getStyleClass().add("label");
    checkInLabel.setLayoutX(25);
    checkInLabel.setLayoutY(560);
    checkInLabel.setAlignment(Pos.CENTER);
    pane.getChildren().add(checkInLabel);

    JFXDatePicker checkInPicker = new JFXDatePicker();
    checkInPicker.setLayoutX(25);
    checkInPicker.setLayoutY(570);
    checkInPicker.setPrefWidth(175);
    checkInPicker.setPrefHeight(40);
    pane.getChildren().add(checkInPicker);
    checkInPicker.setPromptText("Ex: 01/12/2019");

    Label checkOutLabel = new Label("Check-out Date");
    checkOutLabel.setLayoutX(205);
    checkOutLabel.setLayoutY(560);
    checkOutLabel.setAlignment(Pos.CENTER);
    pane.getChildren().add(checkOutLabel);

    JFXDatePicker checkOutPicker = new JFXDatePicker();
    checkOutPicker.setLayoutX(205);
    checkOutPicker.setLayoutY(570);
    checkOutPicker.setPrefWidth(175);
    checkOutPicker.setPrefHeight(40);
    checkOutPicker.setDayCellFactory(
        picker ->
            new DateCell() {
              public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                if(checkInPicker.getValue() != null) {
                  setDisable(empty || date.compareTo(today) < 0
                          || date.compareTo(checkInPicker.getValue()) <= 0);
                } else {
                  setDisable(empty || date.compareTo(today) < 0);
                }
              }
            });
    pane.getChildren().add(checkOutPicker);
    checkOutPicker.setPromptText("Ex: 01/14/2019");

    checkInPicker.setDayCellFactory(
            picker ->
                    new DateCell() {
                      public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        LocalDate today = LocalDate.now();
                        if(checkOutPicker.getValue() != null) {
                          setDisable(empty || date.compareTo(today) < 0
                                  || 0 <= date.compareTo(checkOutPicker.getValue()));
                        } else {
                          setDisable(empty || date.compareTo(today) < 0);
                        }
                      }
                    });

    // Add Button
    JFXButton btn = new JFXButton("Submit");
    btn.setPrefSize(200, 50);
    btn.getStyleClass().add("button-raised");
    btn.setLayoutX(100);
    btn.setLayoutY(640);
    btn.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent e) {
            if ( (nameField.getText() == null || nameField.getText().equals(""))
                    || (emailField.getText() == null || emailField.getText().equals(""))
                    || (addressField.getText() == null || addressField.getText().equals(""))
                    || (creditCardField.getText() == null || creditCardField.getText().equals(""))
                    || checkInPicker.getValue() == null
                    || checkOutPicker.getValue() == null) {
              JFXAlert alert = new JFXAlert(dialog);
              JFXDialogLayout content = new JFXDialogLayout();
              content.setHeading(new Text("Error"));
              content.setBody(new Text("One or more fields have not been filled out!"));
              alert.setSize(400,200);
              alert.setContent(content);
              alert.show();
            }else{
              Random rand = new Random();
              int confNumber = rand.nextInt(89999999) + 10000000;

              // Do something
              System.out.println("Submit Button Pressed" + priceLbl.toString());
              System.out.print(room.getRoomName() + "\n\n\n");
              dialog.close(); // closes booking window
              Stage confirmPopup = new Stage();
              confirmPopup.initModality(Modality.APPLICATION_MODAL);
              VBox popupVbox = new VBox();
              Pane pane = new Pane();
              Label lbl =
                      new Label(
                              "  You're booked! Your confirmation number is: "
                                      + confNumber
                                      + "\n   Please write this number down and keep it safe!"); // TODO: Add
              // confirmation
              pane.getChildren().add(lbl);
              Scene dialogScene =
                      new Scene(popupVbox, 325, 75); // Change confirmation popup size here
              dialogScene.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
              popupVbox.getChildren().add(pane);
              confirmPopup.setScene(dialogScene);
              confirmPopup.show();

              DatabaseManager DBM = new DatabaseManager();
              bookingList.populateFromDB();

              Booking newBooking =
                      new Booking(
                              Integer.toString(confNumber),
                              room.getRoomName(),
                              room.getPrice(),
                              nameField.getText(),
                              addressField.getText(),
                              creditCardField.getText(),
                              emailField.getText(),
                              java.sql.Date.valueOf(checkInPicker.getValue()),
                              java.sql.Date.valueOf(checkOutPicker.getValue()));
              bookingList.addBooking(newBooking);
              room.setVacant(false);

              // adding bookings to database
              DBM.addBooking(newBooking);

              for (Booking b : bookingList.getBookingList()) {
                System.out.print("Room #: " + b.getRoomName() + "\n");
                System.out.print("Check-in Date: " + b.getCheckInDate() + "\n");
                System.out.print("Check-out Date: " + b.getCheckOutDate() + "\n");
              }
              System.out.print("\n\n");
            }
          }
        });
    pane.getChildren().add(btn);

    dialogVbox.getChildren().add(pane);
    Scene dialogScene = new Scene(dialogVbox, 400, 700);
    dialog.setScene(dialogScene);
    dialog.show();
  }
}
