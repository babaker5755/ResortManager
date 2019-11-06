package sample;


import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DRSRModalView extends TableView {

    double popUpWindowHeight = 700.0;
    double popUpWindowWidth = 700.0;

    public void initialize(){

        // create a variable to match the size of the popup and table view
        System.out.println("present report");
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVbox = new VBox(20);

        Pane pane = new Pane();

        TableView drsrTable = new TableView();
        drsrTable.setPrefSize(popUpWindowWidth,popUpWindowHeight);


        dialogVbox.getChildren().add(drsrTable);
        dialogVbox.getChildren().add(pane);
        Scene dialogScene = new Scene(dialogVbox, popUpWindowWidth, popUpWindowHeight);
        dialog.setScene(dialogScene);
        dialog.show();
    }

}
