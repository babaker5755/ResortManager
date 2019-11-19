package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Controller {

  private BookingManager bookingList;
  private DatabaseManager dbManager;

  private boolean hasLoggedInAsManager = false;

  @FXML
  private JFXTabPane tabPane;
  @FXML private ImageView homePageImageView;
  @FXML private ImageView amenitiesPageImageView;
  @FXML private GridPane catalogGridPane;
  @FXML private GridPane managerGridPane;
  @FXML private Pane managerReportFormPane;
  @FXML private JFXButton cancelReservationBtn;
  @FXML private JFXTextField confirmationNumberField;
  @FXML private Label homePageTitleLabel;
  @FXML private Label homePageBodyLabel;
  @FXML private Pane amenitiesPane;

  @FXML
  public void initialize() {
    // Add style to tabs
    for (Tab tab : tabPane.getTabs()) {
      tab.getStyleClass().add("tab");
    }

    homePageBodyLabel.getStyleClass().add("label");
    homePageTitleLabel.getStyleClass().add("title-label");
    cancelReservationBtn.getStyleClass().add("button-raised");
    confirmationNumberField.getStyleClass().add("text-field");

    Image amenitiesImage = new Image(Controller.class.getResourceAsStream("AmenitiesPic.jpg"));
    amenitiesPageImageView.setImage(amenitiesImage);
    Image homePageImage = new Image(Controller.class.getResourceAsStream("BannerPic.jpg"));
    homePageImageView.setImage(homePageImage);

    dbManager = new DatabaseManager();
    ArrayList<Room> rooms = dbManager.getRoomsAsList();
    bookingList = new BookingManager(dbManager.getBookingsAsList());
    setupGridPaneWithRooms(rooms);
    loadManagerTab(rooms);

    // Add Button
    JFXButton roomServiceButton = new JFXButton("Order Room Service");
    roomServiceButton.setPrefSize(200, 50);
    roomServiceButton.getStyleClass().add("button-raised");
    roomServiceButton.setLayoutX(275);
    roomServiceButton.setLayoutY(660);
    roomServiceButton.setOnAction(
            new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent e) {
                RoomServicePane roomServicePane = new RoomServicePane();
                roomServicePane.presentRoomServicePane();
              }
            });
    amenitiesPane.getChildren().add(roomServiceButton);

  }
  @FXML
  void cancelReservation(MouseEvent event) {
    dbManager.removeBookingInList(dbManager.getBookingsAsList(), confirmationNumberField.getText());
    System.out.println(confirmationNumberField.getText());
  }

  private void loadManagerTab(ArrayList<Room> rooms) {
    if (hasLoggedInAsManager) {
      setupManagerGridPane(rooms, false);
      ManagerReportView managerReportView = new ManagerReportView();
      EventHandler<ActionEvent> logoutHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
          hasLoggedInAsManager = false;
          setupManagerGridPane(rooms, true);
          managerReportFormPane.getChildren().remove(managerReportView);
          loadManagerTab(rooms);
        }
      };
      managerReportView.setLogoutHandler(logoutHandler);
      managerReportFormPane.getChildren().add(managerReportView);
    } else {
      ManagerLoginForm loginPane = new ManagerLoginForm();
      loginPane.getStyleClass().add("grid-pane");
      // Pass login action to form
      EventHandler<ActionEvent> loginAction =
              new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
          if (loginPane.authenticate()) {
            managerGridPane.getChildren().remove(loginPane);
            hasLoggedInAsManager = true;
            loadManagerTab(rooms);
          }
        }
      };
      setupManagerGridPane(rooms,true);
      loginPane.setLoginAction(loginAction);
      managerGridPane.getChildren().add(loginPane);
    }
  }

  private void setupManagerGridPane(ArrayList<Room> rooms, boolean setEmpty) {
    managerGridPane.setGridLinesVisible(false);

    // For each room in the list of rooms
    for (int i = 0; i < rooms.size(); i++) {

      // Get row and column of current grid in gridpane
      int row = (i < 5) ? 0 : 1;
      int column = (i < 5) ? i : i - 5;

      // Get specific rooms
      Room room = rooms.get(i);

      // Create pane from room data
      ManagerBrowsePane pane = new ManagerBrowsePane(room);

      // Add pane to specified grid
      if (setEmpty) {
        Pane emptyPane = new Pane();
        emptyPane.getStyleClass().add("pane");
        managerGridPane.add(emptyPane,column,row);
      } else {
        managerGridPane.add(pane, column, row);
      }
    } // end loop
  }

  private void setupGridPaneWithRooms(ArrayList<Room> rooms) {

    catalogGridPane.setGridLinesVisible(false);
    // For each room in the list of rooms
    for (int i = 0; i < rooms.size(); i++) {

      // Get row and column of current grid in gridpane
      int row = (i < 5) ? 0 : 1;
      int column = (i < 5) ? i : i - 5;

      // Add items to the grid
      Room room = rooms.get(i);

      // Create pane from room data
      CatalogBrowsePane pane = new CatalogBrowsePane(room);

      // Add pane to specified grid
      catalogGridPane.add(pane, column, row);
    }
  }
}
