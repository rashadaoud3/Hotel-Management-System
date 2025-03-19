package com.example.project3;

import javafx.animation.FadeTransition; // Library for creating fade animations
import javafx.fxml.FXML; // Library for JavaFX FXML controller
import javafx.fxml.FXMLLoader; // Library for loading FXML files
import javafx.fxml.Initializable; // Library for initializing FXML controllers
import javafx.scene.Node; // Library for JavaFX nodes
import javafx.scene.Parent; // Library for JavaFX parent node
import javafx.scene.Scene; // Library for JavaFX scene
import javafx.scene.control.Button; // Library for JavaFX buttons
import javafx.scene.control.Label; // Library for JavaFX labels
import javafx.scene.input.MouseEvent; // Library for JavaFX mouse events
import javafx.scene.layout.AnchorPane; // Library for JavaFX anchor pane layout
import javafx.stage.Stage; // Library for JavaFX stage
import javafx.util.Duration; // Library for specifying duration in animations

import java.io.IOException; // Library for handling IO exceptions
import java.net.URL; // Library for handling URLs
import java.util.ResourceBundle; // Library for handling resource bundles


public class HomePageController implements Initializable {

    // Annotation used to indicate that the field or method is associated with an FXML view element.
    @FXML
    private Label adminName; // Label for displaying admin name

    @FXML
    private Button bill; // Button for accessing billing feature


    @FXML
    private Button checkin; // Button for accessing check-in feature

    @FXML
    private Button checkout; // Button for accessing check-out feature

    @FXML
    private AnchorPane holdPane; // Anchor pane for holding dynamic content

    @FXML
    private Button room; // Button for accessing room management feature

    private AnchorPane Pane; // Anchor pane for dynamic content

    @FXML
    public static String name; // Static variable to store admin name

    // Annotation used to inform the compiler that the method overrides a method in the superclass.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adminName.setText(name); // Set admin name on initialization
    }
    // Method to set a new node on the anchor pane with fade animation
    private void setNode(Node node) {
        // Clears the children of the holdPane.
        holdPane.getChildren().clear();
        // Adds the specified node to the children of holdPane.
        holdPane.getChildren().add((Node) node);
        // Creates a fade transition with a duration of 1000 milliseconds.
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        // Specifies the node to which the fade effect is applied.
        ft.setNode(node);
        // Sets the initial opacity value of the node.
        ft.setFromValue(0.1);
        // Sets the final opacity value of the node.
        ft.setToValue(1);
        // Specifies the number of cycles in the transition animation.
        ft.setCycleCount(1);
        // Sets whether the transition reverses direction on reaching the end.
        ft.setAutoReverse(false);
        // Starts the fade transition animation.
        ft.play();
    }
    // Method for creating the room management view
    public void createRoom(javafx.event.ActionEvent actionEvent) {
        try {
            // Resetting the styles of other buttons to default.
            checkin.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            checkout.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            bill.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            // Loading the room.fxml file using FXMLLoader.
            Pane = FXMLLoader.load(getClass().getResource("room.fxml"));
            // Setting the loaded node as the active node in the view.
            setNode(Pane);
            // Applying styles to the room button to indicate it's active.
            room.setStyle("-fx-background-color:  #2D3347; -fx-text-fill: #ffffff");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Method for creating the check-in view
    public void createCheckIn(javafx.event.ActionEvent actionEvent) {
        try {
            // Reset styles of other buttons
            room.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            checkout.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            bill.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            // Load checkin.fxml and set it as the current view
            Pane = FXMLLoader.load(getClass().getResource("checkin.fxml"));
            setNode(Pane);
            checkin.setStyle("-fx-background-color:  #2D3347; -fx-text-fill: #ffffff"); // Highlight the check-in button
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method for creating the check-out view
    public void createCheckOut(javafx.event.ActionEvent actionEvent) {
        try {
            // Reset styles of other buttons
            room.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            checkin.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            bill.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            // Load checkout.fxml and set it as the current view
            Pane = FXMLLoader.load(getClass().getResource("checkout.fxml"));
            setNode(Pane);
            checkout.setStyle("-fx-background-color:  #2D3347; -fx-text-fill: #ffffff"); // Highlight the check-out button
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method for creating the customer billing view
    public void createCustomerBill(javafx.event.ActionEvent actionEvent) {
        try {
            // Reset styles of other buttons
            room.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            checkin.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            checkout.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
            // Load bill.fxml and set it as the current view
            Pane = FXMLLoader.load(getClass().getResource("bill.fxml"));
            setNode(Pane);
            bill.setStyle("-fx-background-color:  #2D3347; -fx-text-fill: #ffffff"); // Highlight the bill button
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method for handling logout action
    public void handleLogout(MouseEvent event) throws IOException {
        // Hide current window and show login window
        bill.getScene().getWindow().hide();
        Stage login = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
        login.setScene(scene);
        login.show();
    }
}