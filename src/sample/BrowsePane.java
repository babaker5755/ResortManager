package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class BrowsePane extends Pane {
  Label bedSizeLbl;
  Label numBedsLbl;
  Image roomImage;
  ImageView imgView;
  Label vacantLbl;
  JFXButton btn;
  Label priceLbl;

  public void setBedSizeLblTxt(String text) {
    this.bedSizeLbl.setText(text);
  }

  public void setNumBedsLblTxt(String text) {
    this.numBedsLbl.setText(text);
  }

  public void setImgViewImage(Image image) {
    this.imgView.setImage(image);
  }

  public void setVacantLblTxt(String text) {
    this.vacantLbl.setText(text);
  }

  public void setPriceLblTxt(String text) {
    this.priceLbl.setText(text);
  }

  // For Manager Tab
  public BrowsePane(Room room, boolean forManager) {

    this.getStyleClass().add("pane");
    if (forManager == true) {
      // Add Label
      bedSizeLbl = new Label(room.getBedSize());
      bedSizeLbl.setPrefWidth(150);
      bedSizeLbl.setLayoutX(0);
      bedSizeLbl.setLayoutY(5);
      bedSizeLbl.setAlignment(Pos.CENTER);
      this.getChildren().add(bedSizeLbl);

      // Add Label
      numBedsLbl = new Label("Number of Beds: " + room.getNumBeds());
      numBedsLbl.setPrefWidth(150);
      numBedsLbl.setLayoutX(0);
      numBedsLbl.setLayoutY(20);
      numBedsLbl.setAlignment(Pos.CENTER);
      this.getChildren().add(numBedsLbl);

      // Add Image
      roomImage = new Image(Controller.class.getResourceAsStream("hotelRoom.jpg"));
      imgView = new ImageView(roomImage);
      imgView.setFitHeight(90);
      imgView.setFitWidth(90);
      imgView.setLayoutX(30);
      imgView.setLayoutY(50);
      this.getChildren().add(imgView);

      // Add Label
      vacantLbl = new Label();
      String vacancyText = room.getVacant() ? "Vacant" : "Occupied";
      vacantLbl.setText(vacancyText);
      vacantLbl.setPrefWidth(150);
      vacantLbl.setLayoutX(0);
      vacantLbl.setLayoutY(150);
      vacantLbl.setAlignment(Pos.CENTER);
      this.getChildren().add(vacantLbl);

      // Add Button to view room details
      btn = new JFXButton("View");
      btn.getStyleClass().add("button-raised");
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
      this.getChildren().add(btn);

    } else {
      // For catalog tab
      // Add Label
      bedSizeLbl = new Label(room.getBedSize());
      bedSizeLbl.setPrefWidth(150);
      bedSizeLbl.setLayoutX(0);
      bedSizeLbl.setLayoutY(35);
      bedSizeLbl.setAlignment(Pos.CENTER);
      this.getChildren().add(bedSizeLbl);

      // Add Label
      numBedsLbl = new Label("Number of Beds: " + room.getNumBeds());
      numBedsLbl.setPrefWidth(150);
      numBedsLbl.setLayoutX(0);
      numBedsLbl.setLayoutY(55);
      numBedsLbl.setAlignment(Pos.CENTER);
      this.getChildren().add(numBedsLbl);

      // Add Image
      roomImage = new Image(Controller.class.getResourceAsStream("hotelRoom.jpg"));
      imgView = new ImageView(roomImage);
      imgView.setFitHeight(120);
      imgView.setFitWidth(120);
      imgView.setLayoutX(15);
      imgView.setLayoutY(80);
      this.getChildren().add(imgView);

      // Add Label
      priceLbl = new Label("$" + room.getPrice());
      priceLbl.setPrefWidth(150);
      priceLbl.setLayoutX(0);
      priceLbl.setLayoutY(250);
      priceLbl.setAlignment(Pos.CENTER);
      this.getChildren().add(priceLbl);

      // Add Button to open the "book room" window
      btn = new JFXButton("Book Now");
      btn.getStyleClass().add("button-raised");
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
      this.getChildren().add(btn);
    }
  }
}
