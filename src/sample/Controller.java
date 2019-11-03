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

  boolean hasLoggedInAsManager = false;

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
    loadManagerTab(rooms);
  }

  @FXML
  void cancelReservation(MouseEvent event) {
    dbManager.removeBookingsFromDB(bookingList.getBookingList(), confirmationNumberField.getText());
    System.out.println(confirmationNumberField.getText());
  }

  private void loadManagerTab(ArrayList<Room> rooms) {
    if (hasLoggedInAsManager) {
      setupManagerGridPane(rooms);
      managerReportFormPane.getChildren().add(new ManagerReportView());
    } else {
      ManagerLoginForm loginPane = new ManagerLoginForm();
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

      loginPane.setLoginAction(loginAction);
      managerGridPane.getChildren().add(loginPane);
    }
  }

  private void setupManagerGridPane(ArrayList<Room> rooms) {
    managerGridPane.setGridLinesVisible(false);

    // For each room in the list of rooms
    for (int i = 0; i < rooms.size(); i++) {

      // Get row and column of current grid in gridpane
      int row = (i < 5) ? 0 : 1;
      int column = (i < 5) ? i : i - 5;

      // Get specific rooms
      Room room = rooms.get(i);

      // Create pane from room data
      BrowsePane pane = new BrowsePane(room, true);

      // Add pane to specified grid
      managerGridPane.add(pane, column, row);
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
      BrowsePane pane = new BrowsePane(room, false);

      // Add pane to specified grid
      catalogGridPane.add(pane, column, row);
    }
  }
}
