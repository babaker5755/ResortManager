package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Date;

public class ManagerRoomView {

  Room room;

  public void setRoom(Room room) {
    this.room = room;
  }

  public void presentRoomView() {
    final Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    VBox dialogVbox = new VBox(20);
    dialog.setWidth(700);

    // Add elements to this pane
    Pane pane = new Pane();

    // Add Label
    Label bedSizeLbl = new Label(room.getBedSize());
    bedSizeLbl.setPrefWidth(700);
    bedSizeLbl.setLayoutX(0);
    bedSizeLbl.setLayoutY(15);
    bedSizeLbl.setAlignment(Pos.CENTER);
    pane.getChildren().add(bedSizeLbl);

    // Add Label
    Label numBedsLbl = new Label("Number of Beds: " + room.getNumBeds());
    numBedsLbl.setPrefWidth(700);
    numBedsLbl.setLayoutX(0);
    numBedsLbl.setLayoutY(40);
    numBedsLbl.setAlignment(Pos.CENTER);
    pane.getChildren().add(numBedsLbl);

    Image roomImage = new Image(Controller.class.getResourceAsStream("hotelRoom.jpg"));
    ImageView imgView = new ImageView(roomImage);
    imgView.setFitHeight(135);
    imgView.setFitWidth(135);
    imgView.setLayoutX(284);
    imgView.setLayoutY(75);
    pane.getChildren().add(imgView);

    // Add Label
    Label priceLbl = new Label("$" + room.getPrice());
    priceLbl.setPrefWidth(700);
    priceLbl.setLayoutX(0);
    priceLbl.setLayoutY(235);
    priceLbl.setAlignment(Pos.CENTER);
    pane.getChildren().add(priceLbl);

    final TableView<Booking> tableView = setTableView(new TableView());
    tableView.setPrefWidth(690);
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
    cancelBooking.setLayoutX(250);
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

            yesButton.setOnAction(
              new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                  DatabaseManager DBM = new DatabaseManager();
                  BookingManager bookingList = new BookingManager();
                  bookingList.populateFromDB();

                  TablePosition pos = tableView.getSelectionModel().getSelectedCells().get(0);
                  int row = pos.getRow();

// Item here is the table view type:
                  Booking selectedBooking = tableView.getItems().get(row);
                  bookingList.removeBooking(selectedBooking.getConfirmationNumber());

                  DBM.removeBookingFromDB(selectedBooking);
                }
              }
            );

            noButton.setOnAction(
              new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                  dialog.close();
                }
              }
            );
          }
        });

    pane.getChildren().add(cancelBooking);
  }

  private TableView<Booking> setTableView(TableView<Booking> tableView) {

    DatabaseManager db = new DatabaseManager();
    final ObservableList<Booking> bookingsByRoom =
            FXCollections.observableArrayList(db.getBookingsByRoom(room.getRoomNumber()));
    db.disconnectFromDB();
    tableView.setItems(bookingsByRoom);

    TableColumn<Booking, String> confNumberCol = new TableColumn<>("Confirmation Number");
    confNumberCol.setStyle("-fx-alignment: CENTER;");
    confNumberCol.setCellValueFactory(new PropertyValueFactory<>("confirmationNumber"));
    confNumberCol.setMaxWidth(180);
    tableView.getColumns().add(confNumberCol);

    TableColumn<Booking, String> roomNumberCol = new TableColumn<>("Room Number");
    roomNumberCol.setStyle("-fx-alignment: CENTER;");
    roomNumberCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
    roomNumberCol.setMaxWidth(100);
    tableView.getColumns().add(roomNumberCol);

    TableColumn<Booking, Date> checkInCol = new TableColumn<>("Check-In Date");
    checkInCol.setStyle("-fx-alignment: CENTER;");
    checkInCol.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
    tableView.getColumns().add(checkInCol);

    TableColumn<Booking, Date> checkOutCol = new TableColumn<>("Check-Out Date");
    checkOutCol.setStyle("-fx-alignment: CENTER;");
    checkOutCol.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
    tableView.getColumns().add(checkOutCol);

    TableColumn<Booking, String> clientNameCol = new TableColumn<>("Client Name");
    clientNameCol.setStyle("-fx-alignment: CENTER;");
    clientNameCol.setCellValueFactory(new PropertyValueFactory<>("clientName"));
    clientNameCol.setMinWidth(224);
    tableView.getColumns().add(clientNameCol);

    /* WHY DO THESE MESS THE TABLE UP?
    TableColumn<Booking, String> clientAddressCol = new TableColumn<>("Client Address");
    clientNameCol.setStyle("-fx-alignment: CENTER;");
    clientNameCol.setCellValueFactory(new PropertyValueFactory<>("clientAddress"));
    tableView.getColumns().add(clientAddressCol);

    TableColumn<Booking, String> clientEmailCol = new TableColumn<>("Client Email");
    clientNameCol.setStyle("-fx-alignment: CENTER;");
    clientNameCol.setCellValueFactory(new PropertyValueFactory<>("clientEmail"));
    tableView.getColumns().add(clientEmailCol);
     */

    return tableView;
  }
}
