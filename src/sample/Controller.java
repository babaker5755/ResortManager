package sample;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.TextAlignment;

public class Controller {

  @FXML private TabPane tabPane;
  @FXML private Tab browseCatalogTab;
  @FXML private GridPane catalogGridPane;

  @FXML
  public void initialize() {
    Room[] rooms = createRoomArray();
    setupGridPaneWithRooms(rooms);
  }

  private void setupGridPaneWithRooms(Room[] rooms) {

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
      bedSizeLbl.setPrefWidth(100);
      bedSizeLbl.setLayoutX(20);
      bedSizeLbl.setLayoutY(5);
      pane.getChildren().add(bedSizeLbl);

      // Add Label
      Label numBedsLbl = new Label("Number of Beds: " + room.getNumBeds());
      numBedsLbl.setPrefWidth(120);
      numBedsLbl.setLayoutX(20);
      numBedsLbl.setLayoutY(20);
      pane.getChildren().add(numBedsLbl);


      // To do - Add Image Here
      //
      //

      // Add Label
      Label priceLbl = new Label("$" + room.getPrice());
      priceLbl.setPrefWidth(100);
      priceLbl.setLayoutX(20);
      priceLbl.setLayoutY(170);
      pane.getChildren().add(priceLbl);


      // Add Button
      Button btn = new Button("Book Now");
      btn.setPrefSize(100, 40);
      btn.setLayoutX(10);
      btn.setLayoutY(200);
      pane.getChildren().add(btn);

      catalogGridPane.add(pane,column,row);
    } // end loop

    // Sets 2 rows
    for (int row = 0; row < 2; row++) {
      // Rows should be 50% of gridpane height
      RowConstraints rowConstraint = new RowConstraints();
      rowConstraint.setPercentHeight(50);
      catalogGridPane.getRowConstraints().add(row, rowConstraint);
    }

    // Sets 5 columns
    for (int column = 0; column < 5; column++) {
      // Columns should be 20% of gridpane width
      ColumnConstraints columnConstraint = new ColumnConstraints();
      columnConstraint.setPercentWidth(20);
      catalogGridPane.getColumnConstraints().add(column, columnConstraint);
    }



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
