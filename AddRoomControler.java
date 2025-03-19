package com.example.project3;
import javafx.event.ActionEvent; // Import for handling JavaFX action events.
import javafx.fxml.FXML; // Import for using FXML elements.
import javafx.fxml.Initializable; // Import for initializing FXML controllers.
import javafx.scene.control.Button; // Import for JavaFX button controls.
import javafx.scene.control.Label;
import javafx.scene.control.TextField; // Import for JavaFX text field controls.

import java.net.URL; // Import for handling URLs.
import java.sql.Connection; // Import for establishing database connections.
import java.sql.PreparedStatement; // Import for preparing SQL statements.
import java.sql.ResultSet; // Import for handling SQL result sets.
import java.sql.SQLException; // Import for handling SQL exceptions.
import java.util.ResourceBundle; // Import for handling resource bundles (i.e., localized resources).

import static com.example.project3.RoomController.roomList; // Static import for accessing the roomList from RoomController.
import static com.example.project3.RoomController.room; // Static import for accessing the room observable list from RoomController.

public class AddRoomControler implements Initializable {

    // Static variable to hold the selected room
    public static Room selectedRoom1;

    // FXML annotation for the 'add' button
    @FXML
    private Button add;

    // FXML annotation for the 'delete' button
    @FXML
    private Button delete;

    // FXML annotation for the 'number' text field
    @FXML
    private TextField number;

    // FXML annotation for the 'price' text field
    @FXML
    private TextField price;

    // FXML annotation for the 'type' text field
    @FXML
    private TextField type;

    @FXML
    private Label messageLabel;
    // Connection object for database connectivity
    private Connection connection;

    // DBConnection object for establishing database connection
    private DBConnection dbConnection;

    // PreparedStatement object for executing SQL queries
    private PreparedStatement pst;

    // Method to set the selected room
    public static void setSelectedRoom1(Room room) {
        selectedRoom1 = room;
    }

    // Initialize method to set up database connection
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize DBConnection object
        dbConnection = new DBConnection();
        // Get database connection
        connection = dbConnection.getConnection();
    }

    // Method to handle adding a room
    public void handleAddAction(ActionEvent actionEvent) {
        if (number.getText().isEmpty() || type.getText().isEmpty() || price.getText().isEmpty()) {
            messageLabel.setText("Please fill in all fields.");
            return;
        }

        if (isRoomNumberExists(number.getText())) {
            String suggestedNumber = getSuggestedRoomNumber();
            messageLabel.setText("Room number already exists. Please enter a new one.\nSuggested room number: " + suggestedNumber);
            return;
        }

        String query = "INSERT INTO room (RoomNumber, Type, Ppice) VALUES (?,?,?)";
        try {
            pst = connection.prepareStatement(query);
            pst.setString(1, number.getText());
            pst.setString(2, type.getText());
            pst.setString(3, price.getText());
            pst.executeUpdate();
            messageLabel.setText("Room added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to check if a room number already exists
    private boolean isRoomNumberExists(String roomNumber) {
        String query = "SELECT RoomNumber FROM room WHERE RoomNumber = ?";
        try {
            pst = connection.prepareStatement(query);
            pst.setString(1, roomNumber);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true; // Room number exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Room number does not exist
    }

    // Method to get a suggested room number
    private String getSuggestedRoomNumber() {
        int suggestedNumber = 1;
        while (isRoomNumberExists(String.valueOf(suggestedNumber))) {
            suggestedNumber++;
        }
        return String.valueOf(suggestedNumber);
    }

    public void handleDeleteAction(ActionEvent actionEvent) {
        if (number.getText().isEmpty()) {
            messageLabel.setText("Please enter room number for deletion.");
            return;
        }

        Room roomToDelete = getRoomByNumber(number.getText());

        if (roomToDelete == null) {
            messageLabel.setText("Room number does not exist. Please enter an existing room number.");
            return;
        }

        if (roomToDelete.getStatus().equals("Booked")) {
            messageLabel.setText("The room is booked and cannot be deleted.");
            return;
        }

        String query = "DELETE FROM room WHERE RoomNumber = ?";
        try {
            pst = connection.prepareStatement(query);
            pst.setString(1, number.getText());
            pst.executeUpdate();
            messageLabel.setText("Room deleted successfully.");
        } catch (SQLException e) {
            messageLabel.setText("Error occurred while deleting the room.");
            e.printStackTrace();
        }
    }
    private Room getRoomByNumber(String roomNumber) {
        // Iterate through the room list to find the room with the specified number
        for (Room room : roomList) {
            if (String.valueOf(room.getNumber()).equals(roomNumber)) {
                // Return the room if found
                return room;
            }
        }
        // Return null if no room is found with the specified number
        return null;
    }


}