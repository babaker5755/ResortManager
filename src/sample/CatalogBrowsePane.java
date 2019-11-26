package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class CatalogBrowsePane extends Pane {

  private Label nameLbl;
  private Label numBedsLbl;
  private Image roomImage;
  private ImageView imgView;
  private JFXButton btn;
  private Label priceLbl;

  public CatalogBrowsePane(Room room) {

    this.getStyleClass().add("pane");
    
    // Add Label
    nameLbl = new Label(room.getRoomNumber());
    nameLbl.getStyleClass().add("medium-label");
    nameLbl.setPrefWidth(150);
    nameLbl.setLayoutX(0);
    nameLbl.setLayoutY(30);
    nameLbl.setAlignment(Pos.CENTER);
    this.getChildren().add(nameLbl);


    // Add Label
    numBedsLbl = new Label("Number of Beds: " + room.getNumBeds());
    numBedsLbl.setPrefWidth(150);
    numBedsLbl.setLayoutX(0);
    numBedsLbl.setLayoutY(55);
    numBedsLbl.setAlignment(Pos.CENTER);
    this.getChildren().add(numBedsLbl);

    // Add Image
    roomImage = new Image(Controller.class.getResourceAsStream(room.getImageUrl()));
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

  public void setNumBedsLblTxt(String text) {
    this.numBedsLbl.setText(text);
  }

  public void setImgViewImage(Image image) {
    this.imgView.setImage(image);
  }

  public void setPriceLblTxt(String text) {
    this.priceLbl.setText(text);
  }
}
