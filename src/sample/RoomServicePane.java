package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.print.Book;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class RoomServicePane extends Pane {
    private int numberOfItems = 1;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    ArrayList<JFXComboBox> comboBoxArrayList = new ArrayList<>();

    Pane mainPane = new Pane();
    Pane menuPane = new Pane();

    public void presentRoomServicePane() {
        try {
            // Establish a connection to the database and flag the DBManager as connected.
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:./res/Hotel_Database");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVbox = new VBox(20);

        // Add elements to this pane
        mainPane.setPrefHeight(1000);
        mainPane.getStylesheets().add(Main.class.getResource("Style.css").toExternalForm());
        mainPane.getStyleClass().add("root");

        Label titleLabel = new Label("Room Service");
        titleLabel.getStyleClass().add("title-label");
        titleLabel.setLayoutX(0);
        titleLabel.setLayoutY(20);
        titleLabel.setPrefWidth(400);
        titleLabel.setAlignment(Pos.CENTER);
        mainPane.getChildren().add(titleLabel);

        this.menuPane = setupMenuPane();
        mainPane.getChildren().add(menuPane);

        Label roomNameLabel = new Label("Room Name");
        roomNameLabel.getStyleClass().add("label");
        roomNameLabel.setLayoutX(25);
        roomNameLabel.setLayoutY(400);
        roomNameLabel.setAlignment(Pos.CENTER);
        mainPane.getChildren().add(roomNameLabel);

        JFXTextField roomNameField = new JFXTextField();
        roomNameField.setLayoutX(25);
        roomNameField.setLayoutY(410);
        roomNameField.setPrefWidth(350);
        roomNameField.setPrefHeight(40);
        mainPane.getChildren().add(roomNameField);
        roomNameField.setPromptText("Ex: 1, 2, etc.");

        Label ccLabel = new Label("Credit Card Number");
        ccLabel.getStyleClass().add("label");
        ccLabel.setLayoutX(25);
        ccLabel.setLayoutY(480);
        ccLabel.setAlignment(Pos.CENTER);
        mainPane.getChildren().add(ccLabel);

        JFXTextField creditCardField = new JFXTextField();
        creditCardField.setLayoutX(25);
        creditCardField.setLayoutY(490);
        creditCardField.setPrefWidth(350);
        creditCardField.setPrefHeight(40);
        mainPane.getChildren().add(creditCardField);
        creditCardField.setPromptText("Ex: 5555 5555 5555 5555");

        Label errorLabel = new Label("");
        errorLabel.getStyleClass().add("label");
        errorLabel.setPrefWidth(400);
        errorLabel.setLayoutX(0);
        errorLabel.setLayoutY(600);
        errorLabel.setAlignment(Pos.CENTER);
        mainPane.getChildren().add(errorLabel);

        // Add Button
        JFXButton btn = new JFXButton("Submit Order");
        btn.setPrefSize(200, 50);
        btn.getStyleClass().add("button-raised");
        btn.setLayoutX(100);
        btn.setLayoutY(630);
        btn.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        DatabaseManager databaseManager = new DatabaseManager();
                        ArrayList<Booking> bookings = databaseManager.getBookingsAsList();
                        ArrayList<Date> dateArrayList = new ArrayList<>();
                        if(creditCardField.getText().equals("")
                                || roomNameField.getText().equals("")){
                            new Alert(Alert.AlertType.ERROR,
                                    "Please fill out missing information!").showAndWait();
                        }else if (creditCardCheck(creditCardField.getText())) {
                            try {
                                for(int i = 0; i < bookings.size(); i++){
                                    if (bookings.get(i).getClientCreditCard().equals(creditCardField.getText())) {
                                        dateArrayList.add(new Date(bookings.get(i).getCheckInDate().getTime()));
                                    }
                                }
                                Date latestDate = Collections.max(dateArrayList);
                                double previousCharge = 0;
                                double totalCost = 0;
                                for (int i = 0; i < numberOfItems; i++) {
                                    FoodItem aFoodItem = (FoodItem) comboBoxArrayList.get(i).getSelectionModel().getSelectedItem();
                                    totalCost = totalCost + aFoodItem.getPrice();
                                }
                                ps = conn.prepareStatement(
                                        "SELECT * FROM BOOKINGS WHERE CLIENT_CREDITCARD='"
                                                + creditCardField.getText() + "' AND START_DATE='"
                                                + latestDate + "'");
                                rs = ps.executeQuery();
                                while(rs.next()){
                                   previousCharge = rs.getDouble("TOTAL_CHARGE");
                                }
                                ps = conn.prepareStatement(
                                        "UPDATE BOOKINGS SET TOTAL_CHARGE = "+ (totalCost
                                                + previousCharge)
                                                +" WHERE CLIENT_CREDITCARD= '"
                                                + creditCardField.getText() + "' AND START_DATE='"
                                                + latestDate + "'");
                                int executeUpdate = ps.executeUpdate();

                                errorLabel.setText("Order Submitted");
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }else{
                            new Alert(Alert.AlertType.ERROR,"Information may be incorrect!").showAndWait();
                        }

                    }
                });
        mainPane.getChildren().add(btn);

        dialogVbox.getChildren().add(mainPane);
        Scene dialogScene = new Scene(dialogVbox, 400, 700);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private Pane setupMenuPane() {

        Pane menuPane = new Pane();
        menuPane.setLayoutX(0);
        menuPane.setLayoutY(60);
        menuPane.setPrefHeight(350);
        menuPane.setPrefWidth(400);
        int baseY = 0;
        for (int i = 0; i < numberOfItems; i++) {
            baseY = i * 65;
            Label foodItemLabel = new Label("Food Item");
            foodItemLabel.getStyleClass().add("label");
            foodItemLabel.setLayoutX(25);
            foodItemLabel.setLayoutY(baseY);
            foodItemLabel.setPrefHeight(15);
            foodItemLabel.setAlignment(Pos.CENTER);
            menuPane.getChildren().add(foodItemLabel);

            JFXComboBox<FoodItem> comboBox = new JFXComboBox<FoodItem>();
            ObservableList<FoodItem> foodItemOptions =
                    FXCollections.observableArrayList(
                            FoodItem.Meal,
                            FoodItem.Entree,
                            FoodItem.Beverage,
                            FoodItem.Snack
                    );
            comboBox.setItems(foodItemOptions);
            comboBox.setLayoutY(baseY + 10);
            comboBox.setLayoutX(25);
            comboBox.setPrefWidth(350);
            comboBox.setPrefHeight(40);
            menuPane.getChildren().add(comboBox);
            comboBox.getSelectionModel().selectFirst();
            comboBoxArrayList.add(comboBox);
        }
        // Add Button
        JFXButton btn = new JFXButton("Add Food Item");
        btn.setPrefSize(150, 25);
        btn.getStyleClass().add("button-raised");
        btn.setLayoutX(25);
        btn.setLayoutY(baseY + 60);
        btn.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        addMenuItem();
                    }
                });
        menuPane.getChildren().add(btn);
        return menuPane;
    }

    public boolean creditCardCheck(String creditCard) {
        DatabaseManager databaseManager = new DatabaseManager();
        ArrayList<Booking> bookings = databaseManager.getBookingsAsList();
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getClientCreditCard().equals(creditCard)) {
                return true;
            }
        }
        return false;
    }

    private void addMenuItem() {
        setNumberOfItems(numberOfItems + 1);
        this.mainPane.getChildren().remove(this.menuPane);
        this.menuPane = setupMenuPane();
        this.mainPane.getChildren().add(this.menuPane);
    }

    private void setNumberOfItems(int numberOfItems) {
        if (numberOfItems < 5) {
            this.numberOfItems = numberOfItems;
        }
    }
}
