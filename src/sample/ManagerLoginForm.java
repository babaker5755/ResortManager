package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class ManagerLoginForm extends Pane {
  private TextField usernameField = new TextField();
  private TextField passwordField = new TextField();
  Button btn = new Button("Login");
  Label errorLabel = new Label("");

  public void setLoginAction(EventHandler<ActionEvent> loginAction) {
    this.btn.setOnAction(loginAction);
  }

  public ManagerLoginForm() {

    // Add Label
    Label titleLabel = new Label("Login to continue");
    titleLabel.setPrefWidth(750);
    titleLabel.setLayoutY(55);
    titleLabel.setAlignment(Pos.CENTER);
    this.getChildren().add(titleLabel);

    // Add Label
    Label usernameLabel = new Label("Username");
    usernameLabel.setPrefWidth(250);
    usernameLabel.setLayoutX(250);
    usernameLabel.setLayoutY(120);
    this.getChildren().add(usernameLabel);

    // Add TextField

    usernameField.setPrefWidth(250);
    usernameField.setLayoutX(250);
    usernameField.setLayoutY(140);
    this.getChildren().add(usernameField);

    // Add Label
    Label passwordLabel = new Label("Password");
    passwordLabel.setPrefWidth(250);
    passwordLabel.setLayoutX(250);
    passwordLabel.setLayoutY(200);
    this.getChildren().add(passwordLabel);

    // Add TextField
    passwordField.setPrefWidth(250);
    passwordField.setLayoutX(250);
    passwordField.setLayoutY(220);
    this.getChildren().add(passwordField);

    // Login Button
    btn.setPrefSize(150, 40);
    btn.setLayoutX(300);
    btn.setLayoutY(300);
    this.getChildren().add(btn);

    // Add Label
    errorLabel.setAlignment(Pos.CENTER);
    errorLabel.setPrefWidth(250);
    errorLabel.setLayoutX(250);
    errorLabel.setLayoutY(270);
  }

  public boolean authenticate() {
    String username = usernameField.getText();
    String password = passwordField.getText();
    if (!username.isEmpty() && !password.isEmpty()) {
      this.getChildren().remove(errorLabel);
      this.errorLabel.setText("Success");
      this.getChildren().add(errorLabel);
      // Check username and password here
      return true;
    } else {
      this.getChildren().remove(errorLabel);
      this.errorLabel.setText("Incorrect Credentials");
      this.getChildren().add(errorLabel);
      return false;
    }
  }
}
