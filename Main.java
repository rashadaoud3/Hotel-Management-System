package com.example.project3;

import javafx.application.Application;// Importing necessary JavaFX classes and packages // javafx.application.Application: The base class for JavaFX applications
import javafx.fxml.FXMLLoader;// javafx.fxml.FXMLLoader: Loads an object hierarchy from an XML document
import javafx.scene.Parent;// javafx.scene.Parent: A base class for all nodes that have children in the scene graph
import javafx.scene.Scene;// javafx.scene.Scene: The container for all content in a scene graph
import javafx.stage.Stage;// javafx.stage.Stage: The top-level container for JavaFX applications


// The Main class extends the Application class, which is a JavaFX application framework
public class Main extends Application {

    // The start method is the main entry point for all JavaFX applications
    @Override
    public void start(Stage stage) {
        try {
            // Load the FXML file for the login interface
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            // Create a new scene with the loaded FXML file as the root
            Scene scene = new Scene(root);
            // Set the scene for the primary stage (the main window)
            stage.setScene(scene);
            // Display the stage (window)
            stage.show();
        } catch (Exception exception) {
            // Print the stack trace if an exception occurs
            exception.printStackTrace();
        }
    }

    // The main method, which launches the JavaFX application
    public static void main(String[] args) {
        // Launch the JavaFX application
        launch();
    }
}