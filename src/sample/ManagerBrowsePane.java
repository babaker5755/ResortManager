package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ManagerBrowsePane extends Pane {

  private Label bedSizeLbl;
  private Label numBedsLbl;
  private Image roomImage;
  private ImageView imgView;
  private Label vacantLbl;

  public ManagerBrowsePane(Room room) {

    this.getStyleClass().add("pane");

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
    String vacancyText = room.getVacant() ? "Vacant" : "Occupied";
    vacantLbl = new Label(vacancyText);
    vacantLbl.setPrefWidth(150);
    vacantLbl.setLayoutX(0);
    vacantLbl.setLayoutY(150);
    vacantLbl.setAlignment(Pos.CENTER);
    this.getChildren().add(vacantLbl);

    // Add Button to view room details
    JFXButton btn = new JFXButton("View");
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
  }

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
}
