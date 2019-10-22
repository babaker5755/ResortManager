package sample;

import java.awt.Dimension;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.h2.engine.Database;

public class Controller {

  private BookingManager bookingList;
  private DatabaseManager dbManager;

  @FXML private ImageView homePageImageView;

  @FXML private ImageView amenitiesPageImageView;

  @FXML private GridPane catalogGridPane;

  @FXML private GridPane managerGridPane;

  @FXML private Pane managerReportFormPane;

  @FXML private Button cancelReservationBtn;

  @FXML private PasswordField confirmationNumberField;

  @FXML
  public void initialize() {

    Image amenitiesImage = new Image(Controller.class.getResourceAsStream("AmenitiesPic.jpg"));
    amenitiesPageImageView.setImage(amenitiesImage);
    Image homePageImage = new Image(Controller.class.getResourceAsStream("BannerPic.jpg"));
    homePageImageView.setImage(homePageImage);

    dbManager = new DatabaseManager();
    ArrayList<Room> rooms = dbManager.getRoomsAsList();
    bookingList = new BookingManager(dbManager.getBookingsAsList());
    setupGridPaneWithRooms(rooms);
    setupManagerGridPane(rooms);
    setupManagerReportForm();
  }

  @FXML
  void cancelReservation(MouseEvent event) {
    dbManager.removeBookingsFromDB(bookingList.getBookingList(), confirmationNumberField.getText());
    System.out.println(confirmationNumberField.getText());
  }

  private void setupManagerReportForm() {

    // Add elements to the managerReportFormPane

    // Add Label
    Label titleLabel = new Label("Generate Report");
    titleLabel.setPrefWidth(750);
    titleLabel.setLayoutY(15);
    titleLabel.setAlignment(Pos.CENTER);
    managerReportFormPane.getChildren().add(titleLabel);

    // Add Label
    Label startLabel = new Label("Start Date");
    startLabel.setPrefWidth(250);
    startLabel.setLayoutX(50);
    startLabel.setLayoutY(50);
    managerReportFormPane.getChildren().add(startLabel);

    // Add Date Picker
    DatePicker startDatePicker = new DatePicker();
    startDatePicker.setPrefWidth(250);
    startDatePicker.setLayoutX(50);
    startDatePicker.setLayoutY(70);
    managerReportFormPane.getChildren().add(startDatePicker);

    // Add Label
    Label endLabel = new Label("End Date");
    endLabel.setPrefWidth(250);
    endLabel.setLayoutX(450);
    endLabel.setLayoutY(50);
    managerReportFormPane.getChildren().add(endLabel);

    // Add Date Picker
    DatePicker endDatePicker = new DatePicker();
    endDatePicker.setPrefWidth(250);
    endDatePicker.setLayoutX(450);
    endDatePicker.setLayoutY(70);
    managerReportFormPane.getChildren().add(endDatePicker);

    // Add Button
    Button btn = new Button("Generate Report");
    btn.setPrefSize(150, 40);
    btn.setLayoutX(300);
    btn.setLayoutY(140);
    btn.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent e) {
            System.out.println("present report");
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            VBox dialogVbox = new VBox(20);

            Pane pane = new Pane();
            pane.getChildren().add(new TableView<>());

            dialogVbox.getChildren().add(pane);
            Scene dialogScene = new Scene(dialogVbox, 250, 400);
            dialog.setScene(dialogScene);
            dialog.show();
          }
        });
    managerReportFormPane.getChildren().add(btn);
  }

  private void setupManagerGridPane(ArrayList<Room> rooms) {
    managerGridPane.setGridLinesVisible(false);

    // For each room in the list of rooms
    for (int i = 0; i < rooms.size(); i++) {

      // Get row and column of current grid in gridpane
      int row = (i < 5) ? 0 : 1;
      int column = (i < 5) ? i : i - 5;

      // Add items to the grid
      Room room = rooms.get(i);

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
      imgView.setFitHeight(90);
      imgView.setFitWidth(90);
      imgView.setLayoutX(30);
      imgView.setLayoutY(50);
      pane.getChildren().add(imgView);

      // Add Label
      Label vacantLbl = new Label();
      String vacancyText = room.getVacant() ? "Vacant" : "Occupied";
      vacantLbl.setText(vacancyText);
      vacantLbl.setPrefWidth(150);
      vacantLbl.setLayoutX(0);
      vacantLbl.setLayoutY(150);
      vacantLbl.setAlignment(Pos.CENTER);
      pane.getChildren().add(vacantLbl);

      // Add Button
      Button btn = new Button("View");
      btn.setPrefSize(60, 30);
      btn.setLayoutX(45);
      btn.setLayoutY(175);
      btn.setOnAction(
          new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
              ManagerRoomView roomView = new ManagerRoomView();
              roomView.setRoom(room);
              roomView.presentRoomView();
              System.out.println("Manager Room View presented");
            }
          });
      pane.getChildren().add(btn);

      managerGridPane.add(pane, column, row);
    } // end loop
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
              RoomView roomView = new RoomView();
              roomView.setRoom(room);
              roomView.presentRoomView();
              System.out.println("button pressed" + priceLbl.toString());
            }
          });
      pane.getChildren().add(btn);

      catalogGridPane.add(pane, column, row);
    }
  }

  private void setupGridPaneWithRooms(ArrayList<Room> rooms) {

    catalogGridPane.setGridLinesVisible(false);
    // For each room in the list of rooms
    for (int i = 0; i < rooms.size(); i++) {

      if (rooms.get(i).getVacant() == true) {
        // Get row and column of current grid in gridpane
        int row = (i < 5) ? 0 : 1;
        int column = (i < 5) ? i : i - 5;

        // Add items to the grid
        Room room = rooms.get(i);

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
                RoomView roomView = new RoomView();
                roomView.setRoom(room);
                roomView.setBookingList(bookingList);
                roomView.presentRoomView();
                System.out.println("button pressed " + priceLbl.toString());
              }
            });
        pane.getChildren().add(btn);

        catalogGridPane.add(pane, column, row);
        System.out.print(rooms.get(i).getRoomNumber() + " added to view.\n");
      } else {
        System.out.print(rooms.get(i).getRoomNumber() + " is occupied!\n");
      }
    } // end loop
  }
}
