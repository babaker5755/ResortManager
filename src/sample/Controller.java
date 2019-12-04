package sample;

import com.jfoenix.controls.*;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.crypto.Data;

public class Controller {

  private BookingManager bookingList;
  private DatabaseManager dbManager;

  private boolean hasLoggedInAsManager = false;

  @FXML private JFXTabPane tabPane;
  @FXML private ImageView homePageImageView;
  @FXML private ImageView amenitiesPageImageView;
  @FXML private GridPane catalogGridPane;
  @FXML private GridPane managerGridPane;
  @FXML private Pane managerReportFormPane;
  @FXML private JFXButton cancelReservationBtn;
  @FXML private JFXTextField confirmationNumberField;
  @FXML private Label homePageTitleLabel;
  @FXML private Pane homeScreenPane;
  @FXML private GridPane homeScreenGridPane;
  @FXML private GridPane amenitiesGridPane;

  @FXML
  public void initialize() {
    // Add style to tabs
    for (Tab tab : tabPane.getTabs()) {
      tab.getStyleClass().add("tab");
    }

    tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>()
    {
      @Override
      public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1)
      {
        DatabaseManager databaseManager = new DatabaseManager();
        ArrayList<Room> rooms = databaseManager.getRoomsAsList();

        for (Room room : rooms) {
          ArrayList<Booking> bookingsForRoom = databaseManager.getBookingsByRoom(room.getRoomName());
          LocalDate today = LocalDate.now();
          boolean isVacant = true;

          for (Booking booking : bookingsForRoom) {

            if (today.compareTo(
                booking.getCheckInDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) >= 0
                && today.compareTo(
                booking.getCheckOutDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) <= 0) {
              isVacant = false;
            }
          }
          databaseManager.setIsVacant(room.getRoomName(), isVacant);
        }
      }
    });

    homePageTitleLabel.getStyleClass().add("title-label");
    cancelReservationBtn.getStyleClass().add("button-raised");
    confirmationNumberField.getStyleClass().add("text-field");

    Image amenitiesImage =
        new Image(Controller.class.getResourceAsStream("images/AmenitiesPic.jpg"));
    amenitiesPageImageView.setImage(amenitiesImage);
    Image homePageImage = new Image(Controller.class.getResourceAsStream("images/BannerPic.jpg"));
    homePageImageView.setImage(homePageImage);

    dbManager = new DatabaseManager();
    ArrayList<Room> rooms = dbManager.getRoomsAsList();
    bookingList = new BookingManager(dbManager.getBookingsAsList());
    setupGridPaneWithRooms(rooms);
    loadManagerTab(rooms);
    loadHomeScreenPane();
    setupAmenitiesGridPane();
  }

  @FXML
  void cancelReservation(MouseEvent event) {
    Stage stage = (Stage) homeScreenPane.getScene().getWindow();
    if (confirmationNumberField.getText() != null
        && !confirmationNumberField.getText().equals("")) {
      dbManager.removeBookingInList(
          dbManager.getBookingsAsList(), confirmationNumberField.getText());
      JFXAlert alert = new JFXAlert(stage);
      JFXDialogLayout content = new JFXDialogLayout();
      Text heading = new Text("Success");
      heading.setFont(Font.font("Verdana", 24));
      content.setHeading(heading);
      Text contentText =
          new Text("The booking associated with your confirmation number has been cancelled.");
      contentText.setFont(Font.font("Verdana", 12));
      content.setBody(contentText);
      alert.setSize(600, 200);
      alert.setContent(content);
      alert.show();
    } else {
      JFXAlert alert = new JFXAlert(stage);
      JFXDialogLayout content = new JFXDialogLayout();
      Text heading = new Text("Missing confirmation number");
      heading.setFont(Font.font("Verdana", 24));
      content.setHeading(heading);
      Text contentText = new Text("Please enter a confirmation number to cancel your booking.");
      contentText.setFont(Font.font("Verdana", 12));
      content.setBody(contentText);
      alert.setSize(600, 200);
      alert.setContent(content);
      alert.show();
    }
  }

  private void loadHomeScreenPane() {
    homeScreenGridPane.getStyleClass().add("grid-pane");
    for (int i = 0; i < 9; i++) {
      boolean setEmpty = false;
      int row = (i > 2) ? (i > 5) ? 2 : 1 : 0;
      int column = (i > 2) ? (i > 5) ? i - 6 : i - 3 : i;

      // Create pane from room data
      Pane pane = new Pane();
      pane.prefWidth(230);
      Label lbl = new Label();
      Label titleLabel = new Label();
      if (i != 0){
        titleLabel.setLayoutY(-10);
      }
      titleLabel.prefWidth(230);
      titleLabel.getStyleClass().add("title-label");
      titleLabel.setAlignment(Pos.CENTER);
      pane.getChildren().add(titleLabel);
      switch (i) {
        case 0 :
          titleLabel.setText("Book a room");
          lbl.setText("If you would like to book a room at \nimport resortName, simply click \nthe \"Browse Catalog\" button at the \ntop and pick from our wide \nselection of state-of-the-art \nrooms."); break;
        case 4 :
          titleLabel.setText("Manage rooms");
          lbl.setText("If you are a staff manager, simply\nclick the \"Manager\" tab and input\n your username and password when \nprompted, allowing you access to\n our secure server and the ability\n to view different aspects of resort \nand client information."); break;
        case 8 :
          titleLabel.setText("Discover Amenities");
          lbl.setText("If you'd like to see all the wonderful \nthings available to guests here at \nthe resort, click the \"Amenities\"\n tab at the top of this page and be \ngreeted with a vast description of \nall the different ways you can relax \nwhile staying at import resortName!"); break;
        default:  setEmpty = true;
      }if (i == 0){
        lbl.setLayoutY(30);
      } else {
        lbl.setLayoutY(20);
      }
      lbl.setPrefWidth(230);
      lbl.getStyleClass().add("label");
      pane.getChildren().add(lbl);
      // Add pane to specified grid
      if (setEmpty) {
        Pane emptyPane = new Pane();
        emptyPane.getStyleClass().add("pane");
        homeScreenGridPane.add(emptyPane, column, row);
      } else {
        homeScreenGridPane.add(pane, column, row);
      }
    }

  }

  private void loadManagerTab(ArrayList<Room> rooms) {
    managerReportFormPane.getStyleClass().add("grid-pane");
    managerGridPane.getStyleClass().add("grid-pane");
    if (hasLoggedInAsManager) {
      setupManagerGridPane(rooms, false);
      ManagerReportView managerReportView = new ManagerReportView();
      managerReportView.getStyleClass().add("grid-pane");
      EventHandler<ActionEvent> logoutHandler =
          new EventHandler<ActionEvent>() {
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
      setupManagerGridPane(rooms, true);
      loginPane.setLoginAction(loginAction);
      managerGridPane.getChildren().add(loginPane);
    }
  }

  private void setupManagerGridPane(ArrayList<Room> rooms, boolean setEmpty) {
    managerGridPane.setGridLinesVisible(false);

    if(setEmpty){
      managerGridPane.getChildren().clear();

    }

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
        managerGridPane.add(emptyPane, column, row);
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

  private void setupAmenitiesGridPane() {
    List<String> listOfAmenities =
        Arrays.asList(
            "Order Room Service",
            "Gym, Pool, and Track",
            "Wedding Packages",
            "Arcade & Minigames",
            "Private Beach",
            "Private Ski Slopes");

    amenitiesGridPane.getStyleClass().add("grid-pane");
    amenitiesGridPane.setGridLinesVisible(false);
    // For each room in the list of rooms
    for (int i = 0; i < listOfAmenities.size(); i++) {

      // Get row and column of current grid in gridpane
      int row = (i < 3) ? 0 : 1;
      int column = (i < 3) ? i : i - 3;

      Pane pane = new Pane();
      JFXButton button = new JFXButton(listOfAmenities.get(i));
      button.getStyleClass().add("button-raised");
      button.setPrefHeight(140);
      button.setPrefWidth(240);
      button.setAlignment(Pos.CENTER);
      int finalI = i;
      button.setOnAction(
          new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
              switch (listOfAmenities.get(finalI)) {
                case "Order Room Service":
                  RoomServicePane roomServicePane = new RoomServicePane();
                  roomServicePane.presentRoomServicePane();
                  return;
                default:
                  presentAmenityInfo(listOfAmenities.get(finalI));
              }
            }
          });
      pane.getChildren().add(button);

      // Add pane to specified grid
      amenitiesGridPane.add(pane, column, row);
    }
  }

  public void presentAmenityInfo(String amenity) {
    final Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    VBox dialogVbox = new VBox(20);
    Pane pane = new Pane();

    Label titleLabel = new Label(amenity);
    titleLabel.getStyleClass().add("title-label");
    titleLabel.setLayoutX(0);
    titleLabel.setLayoutY(20);
    titleLabel.setPrefWidth(400);
    titleLabel.setAlignment(Pos.CENTER);
    pane.getChildren().add(titleLabel);

    dialogVbox.getChildren().add(pane);
    Scene dialogScene = new Scene(dialogVbox, 400, 600);
    dialog.setScene(dialogScene);

    switch (amenity) {
      case "Order Room Service":
        // There's no way I'm touching THAT.
        break;
      case "Gym, Pool, and Track":
        Label amenitiesTextGym =
            new Label(
                "Are you the type of person who never is happy unless they're working\n"
                    + "out? Do you constantly wish that you were lifting weights with your bros,\neven when on vacation? Well, "
                    + "at our state of the art gym, open 24/7 for\nguests, you will be in workout heaven. With over three "
                    + "hundred\ntreadmills, four hundred weight benches, and over ten tons of assorted\nbarbells, dumbbells, "
                    + "and other types of weights. Our indoor bicycle\ntrack and swimming pool are also available for you "
                    + "to use no matter the\ntime or day.");
        amenitiesTextGym.getStyleClass().add("text-field");
        amenitiesTextGym.setLayoutX(0);
        amenitiesTextGym.setLayoutY(50);
        amenitiesTextGym.setPrefWidth(400);
        amenitiesTextGym.setAlignment(Pos.CENTER);
        Image image = new Image("sample/images/gym.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(400);
        imageView.setX(0);
        imageView.setLayoutY(200);
        pane.getChildren().add(imageView);
        pane.getChildren().add(amenitiesTextGym);
        break;
      case "Wedding Packages":
        Label amenitiesTextWedding =
            new Label(
                "If you're planning on sharing your special day with us here at\n"
                    + "import resortName, then do we have a terrific selection of packages for\nyou! Whether it's your "
                    + "highschool sweetheart or the cute towel boy you\njust met at the cabana, rest assured that your wedding "
                    + "will be as magical\nas ever. Our Starfish Package has your ceremony take place at one of our\n"
                    + "private beaches under the moonlight, perfect for those looking for a\nromantic memory to cherish. Our "
                    + "Here Comes The Bride Package takes\nplace in our special chateau atop our private mountain range in "
                    + "France.\nWith the price of that one, we definitely put the steep in steeple! Neither\nof those catch "
                    + "your eye? The our Custom Package is the way to go!\nSimply tell us your request and we'll take care "
                    + "of everything.");

        amenitiesTextWedding.getStyleClass().add("text-field");
        amenitiesTextWedding.setLayoutX(0);
        amenitiesTextWedding.setLayoutY(50);
        amenitiesTextWedding.setPrefWidth(400);
        amenitiesTextWedding.setAlignment(Pos.CENTER);
        image = new Image("sample/images/wedding.jpg");
        imageView = new ImageView(image);
        imageView.setFitHeight(250);
        imageView.setFitWidth(400);
        imageView.setX(0);
        imageView.setLayoutY(250);
        pane.getChildren().add(imageView);
        pane.getChildren().add(amenitiesTextWedding);
        break;
      case "Arcade & Minigames":
        Label amenitiesTextArcade =
            new Label(
                "Our Arcade room has every game and system ever created (even those\n"
                    + "rare Japanese ones!) We've worked hard to ensure that we've obtained\neach and every game that is "
                    + "or was ever available for purchase, and a\nconsole to go with each one. Play your friends at a friendly "
                    + "game of\nMortal Street Fighting 1, or the new Mortal Street Fighting 13 that has\nyet to even come out "
                    + "yet! Wanting something a bit more exciting? Our\nindoor go-kart track is right around the corner, "
                    + "along with our laser tag\narena, trivia gameshow room, and bowling alley!");
        amenitiesTextArcade.getStyleClass().add("text-field");
        amenitiesTextArcade.setLayoutX(0);
        amenitiesTextArcade.setLayoutY(50);
        amenitiesTextArcade.setPrefWidth(400);
        amenitiesTextArcade.setAlignment(Pos.CENTER);
        image = new Image("sample/images/arcade.jpg");
        imageView = new ImageView(image);
        imageView.setFitHeight(250);
        imageView.setFitWidth(400);
        imageView.setX(0);
        imageView.setLayoutY(200);
        pane.getChildren().add(imageView);
        pane.getChildren().add(amenitiesTextArcade);
        break;
      case "Private Beach":
        Label amenitiesTextBeach =
            new Label(
                "Sit back and relax at one of our three dozen private beaches, all"
                    + "\naccessible by our own chartered boats. Take a nap on one of our islands\nin the Caribbean, then set "
                    + "sail to a different beach off the coast of\nSouth America. Looking for something a little more exotic? "
                    + "Our beaches in\nGreece, Italy, and Morocco are only a quick trip away with our private jet\nrentals. With"
                    + " locations all over the world, you can find a place in any continent\nyou'd like to soak up some sun, "
                    + "enjoy the water, and sip on mimosas all\nday long.");
        amenitiesTextBeach.getStyleClass().add("text-field");
        amenitiesTextBeach.setLayoutX(0);
        amenitiesTextBeach.setLayoutY(50);
        amenitiesTextBeach.setPrefWidth(400);
        image = new Image("sample/images/beach.jpg");
        imageView = new ImageView(image);
        imageView.setFitHeight(250);
        imageView.setFitWidth(400);
        imageView.setX(0);
        imageView.setLayoutY(200);
        pane.getChildren().add(imageView);
        amenitiesTextBeach.setAlignment(Pos.CENTER);

        pane.getChildren().add(amenitiesTextBeach);
        break;
      case "Private Ski Slopes":
        Label amenitiesTextSki =
            new Label(
                "Who needs beaches and arcades when you have some slopes? Take a\nplane "
                    + "to any of our private ski resorts and shred that fresh snow,\nbrochacho. With resorts all over the world, "
                    + "we have a location for you\nwherever you'd like to be. Our in-house ski and snowboard\nmanufacturers "
                    + "will design a set of skiis just for you, perfectly handcrafted\nto your exact needs.");
        amenitiesTextSki.getStyleClass().add("text-field");
        amenitiesTextSki.setLayoutX(0);
        amenitiesTextSki.setLayoutY(50);
        amenitiesTextSki.setPrefWidth(400);
        amenitiesTextSki.setAlignment(Pos.CENTER);
        image = new Image("sample/images/ski.jpg");
        imageView = new ImageView(image);
        imageView.setFitHeight(250);
        imageView.setFitWidth(400);
        imageView.setX(0);
        imageView.setLayoutY(150);
        pane.getChildren().add(imageView);
        pane.getChildren().add(amenitiesTextSki);
        break;
    }

    dialog.show();
  }
}
