package sample;

import java.awt.Dimension;
import java.util.Collection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller {

  @FXML private GridPane catalogGridPane;

  @FXML
  public void initialize() {
    Room[] rooms = createRoomArray();
    setupGridPaneWithRooms(rooms);
  }

  private void setupGridPaneWithRooms(Room[] rooms) {

    catalogGridPane.setGridLinesVisible(false);
    // For each room in the list of rooms
    for (int i = 0; i < rooms.length; i++) {

      // Get row and column of current grid in gridpane
      int row = (i < 5) ? 0 : 1;
      int column = (i < 5) ? i : i - 5;

      // Add items to the grid
      Room room = rooms[i];

      // Add elements to this pane
      Pane pane = new Pane();

      // Add Label
      Label bedSizeLbl = new Label(room.getBedSize());
      bedSizeLbl.setPrefWidth(150);
      bedSizeLbl.setLayoutX(0);
      bedSizeLbl.setLayoutY(5);
      bedSizeLbl.setAlignment(Pos.CENTER);
      pane.getChildren().add(bedSizeLbl);

      // Add Label
      Label numBedsLbl = new Label("Number of Beds: " + room.getNumBeds());
      numBedsLbl.setPrefWidth(150);
      numBedsLbl.setLayoutX(0);
      numBedsLbl.setLayoutY(20);
      numBedsLbl.setAlignment(Pos.CENTER);
      pane.getChildren().add(numBedsLbl);

      Image roomImage = new Image(Controller.class.getResourceAsStream("hotelRoom.jpg"));
      ImageView imgView = new ImageView(roomImage);
      imgView.setFitHeight(120);
      imgView.setFitWidth(120);
      imgView.setLayoutX(15);
      imgView.setLayoutY(45);
      pane.getChildren().add(imgView);

      // Add Label
      Label priceLbl = new Label("$" + room.getPrice());
      priceLbl.setPrefWidth(150);
      priceLbl.setLayoutX(0);
      priceLbl.setLayoutY(250);
      priceLbl.setAlignment(Pos.CENTER);
      pane.getChildren().add(priceLbl);


      // Add Button
      Button btn = new Button("Book Now");
      btn.setPrefSize(120, 40);
      btn.setLayoutX(15);
      btn.setLayoutY(275);
      btn.setOnAction(
          new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
              // Do something
              presentRoomView(room);
              System.out.println("button pressed" + priceLbl.toString());
            }
          });
      pane.getChildren().add(btn);

      catalogGridPane.add(pane,column,row);
    } // end loop


  }

  private void presentRoomView(Room room) {
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

  private Room[] createRoomArray() {
    Room[] rooms = {
      new Room("01", "King", 1, true, 150.00),
      new Room("02", "Queen", 2, true, 150.00),
      new Room("03", "King", 1, true, 850.00),
      new Room("04", "Queen", 2, true, 150.00),
      new Room("05", "King", 1, true, 140.00),
      new Room("06", "Queen", 2, true, 150.00),
      new Room("07", "King", 1, true, 30.00),
      new Room("08", "Queen", 2, true, 650.00),
      new Room("09", "King", 1, true, 290.00),
      new Room("10", "Queen", 2, true, 160.00)
    };
    return rooms;
  }
}
