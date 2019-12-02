package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Date;

public class DRSRModalView extends TableView {
  double popUpWindowHeight = 700.0;
  double popUpWindowWidth = 1150.0;

  public void initialize(Date startDate, Date endDate) {

    // create a variable to match the size of the popup and table view
    System.out.println("present report");
    final Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    VBox dialogVbox = new VBox(20);

    Pane pane = new Pane();

    TableView<Booking> drsrTable = new TableView();
    drsrTable.setPrefSize(popUpWindowWidth,popUpWindowHeight);
    setTableView(drsrTable, startDate, endDate);


    dialogVbox.getChildren().add(drsrTable);
    dialogVbox.getChildren().add(pane);
    Scene dialogScene = new Scene(dialogVbox, popUpWindowWidth, popUpWindowHeight);
    dialog.setScene(dialogScene);
    dialog.show();
  }
  private TableView<Booking> setTableView(TableView<Booking> tableView, Date startDate, Date endDate){
    DatabaseManager db = new DatabaseManager();
    ArrayList<Booking> bookingList = db.getBookingsAsList();
    final ObservableList<Booking> storedBookings =
            FXCollections.observableArrayList();
    for(int i = 0; i < db.getBookingsAsList().size(); i++){
      if( ((startDate.before(bookingList.get(i).getCheckInDate()) && endDate.after(bookingList.get(i).getCheckOutDate()))
      || (startDate.equals(bookingList.get(i).getCheckInDate()) && (endDate.after(bookingList.get(i).getCheckOutDate())))
      || (startDate.equals(bookingList.get(i).getCheckInDate()) && (endDate.equals(bookingList.get(i).getCheckOutDate())))
      ) && !(startDate.after(bookingList.get(i).getCheckInDate()))){
        storedBookings.add(bookingList.get(i));
      }
    }
    db.disconnectFromDB();
    tableView.setItems(storedBookings);

    TableColumn<Booking, String> confNumberCol = new TableColumn<>("Confirmation Number");
    confNumberCol.setStyle("-fx-alignment: CENTER;");
    confNumberCol.setCellValueFactory(new PropertyValueFactory<>("confirmationNumber"));
    confNumberCol.setMaxWidth(180);
    tableView.getColumns().add(confNumberCol);

    TableColumn<Booking, String> roomNameCol = new TableColumn<>("Room Name");
    roomNameCol.setStyle("-fx-alignment: CENTER;");
    roomNameCol.setCellValueFactory(new PropertyValueFactory<>("roomName"));
    roomNameCol.setMaxWidth(100);
    tableView.getColumns().add(roomNameCol);

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
    tableView.getColumns().add(clientNameCol);

    TableColumn<Booking, String> clientAddressCol = new TableColumn<>("Client Address");
    clientAddressCol.setStyle("-fx-alignment: CENTER;");
    clientAddressCol.setCellValueFactory(new PropertyValueFactory<>("clientAddress"));
    tableView.getColumns().add(clientAddressCol);

    TableColumn<Booking, String> clientCreditCardCol = new TableColumn<>("Client Credit Card");
    clientCreditCardCol.setStyle("-fx-alignment: CENTER;");
    clientCreditCardCol.setCellValueFactory(new PropertyValueFactory<>("clientCreditCard"));
    tableView.getColumns().add(clientCreditCardCol);

    TableColumn<Booking, String> clientEmailCol = new TableColumn<>("Client E-Mail");
    clientEmailCol.setStyle("-fx-alignment: CENTER;");
    clientEmailCol.setCellValueFactory(new PropertyValueFactory<>("clientEmail"));
    tableView.getColumns().add(clientEmailCol);

    TableColumn<Booking, String> chargeCol = new TableColumn<>("Charge");
    chargeCol.setStyle("-fx-alignment: CENTER;");
    chargeCol.setCellValueFactory(new PropertyValueFactory<>("charge"));
    tableView.getColumns().add(chargeCol);

    return tableView;
  }
}
