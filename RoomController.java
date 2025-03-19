package com.example.project3;

// Import statements for JavaFX collections classes for managing observable lists.
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Import statements for JavaFX FXML related classes.
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

// Import statements for JavaFX scene and control classes.
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

// Import statements for Java I/O and SQL related classes.
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

// Controller class for managing room-related operations.
public class RoomController implements Initializable {

    // Reference to the price column in the TableView.
    @FXML
    private TableColumn<Room, String> price;

    // Reference to the roomNumber column in the TableView.
    @FXML
    private TableColumn<Room, String> roomNumber;

    // Reference to the roomTable TableView.
    @FXML
    private TableView<Room> roomTable;

    // Reference to the roomType column in the TableView.
    @FXML
    private TableColumn<Room, String> roomType;

    // Reference to the search TextField.
    @FXML
    private TextField search;

    // Reference to the status column in the TableView.
    @FXML
    private TableColumn<Room, String> status;

    // Connection object for database operations.
    private Connection connection;

    // Database connection object.
    private DBConnection dbConnection;

    // Prepared statement for executing SQL queries.
    private PreparedStatement pst;

    // ObservableList to store room data for use in JavaFX TableView.
    public static final ObservableList<Room> room= FXCollections.observableArrayList();

    // List to store room data fetched from the database.
    public static final List<Room> roomList = new ArrayList<>();

    // Initialize method called when the FXML file is loaded.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Establish database connection.
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();

        // Set cell value factories for each column in the TableView.
        roomNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        roomType.setCellValueFactory(new PropertyValueFactory<>("type"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        status.setCellValueFactory(new PropertyValueFactory<Room, String>("status"));

        // Load room data from the database.
        try {
            initRoomList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set the items in the TableView.
        roomTable.setItems(room);
    }

    // Method to handle the action of adding a new room.
    public void handleAddAction(javafx.event.ActionEvent actionEvent) throws IOException {
        // Create a new stage for the add room window.
        Stage add = new Stage();
        // Load the FXML file for adding a room.
        Parent root = FXMLLoader.load(getClass().getResource("addroom.fxml"));
        // Create a new scene with the root node.
        Scene scene = new Scene(root);
        // Set the scene for the add stage.
        add.setScene(scene);
        // Show the add stage.
        add.show();
    }

    // Method to handle the action of deleting a room.
    public void handleDeleteAction(javafx.event.ActionEvent actionEvent) throws IOException {
        // Create a new stage for the delete room window.
        Stage add = new Stage();
        // Load the FXML file for deleting a room.
        Parent root = FXMLLoader.load(getClass().getResource("deleteroom.fxml"));
        // Create a new scene with the root node.
        Scene scene = new Scene(root);
        // Set the scene for the delete stage.
        add.setScene(scene);
        // Show the delete stage.
        add.show();
    }


    // Method to handle the double-click event on a row in the room table.
    public void clickItem(MouseEvent event) throws IOException {
        // Check if the row is double-clicked.
        if (event.getClickCount() == 2) {
            // Check if an item is selected in the table.
            if (roomTable.getSelectionModel().getSelectedItem() != null) {
                // Check if the selected room is booked.
                if (roomTable.getSelectionModel().getSelectedItem().getStatus().equals("Booked")) {
                    // Set the selected room number in the GuestController.
                    GuestController.setSelectedRoomNumber(roomTable.getSelectionModel().getSelectedItem().getNumber());
                    // Create a new stage for the customer info window.
                    Stage add = new Stage();
                    // Load the FXML file for the customer info window.
                    Parent root = FXMLLoader.load(getClass().getResource("customerinfo.fxml"));
                    // Create a new scene with the root node.
                    Scene scene = new Scene(root);
                    // Set the scene for the stage.
                    add.setScene(scene);
                    // Show the stage.
                    add.show();
                }
            }
        }
    }

    // Method to fetch room data from the database and initialize room lists.
    public void initRoomList() throws IOException {
        // Clear existing room lists.
        roomList.clear();
        room.clear();
        // SQL query to select all rooms from the database.
        String query = "SELECT * FROM room";
        try {
            // Prepare and execute the SQL query.
            pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            // Loop through the result set and add rooms to the lists.
            while (rs.next()) {
                int room_price = Integer.parseInt(rs.getString("Ppice"));
                String room_type = rs.getString("Type");
                String room_status = rs.getString("Status");
                int room_num = Integer.parseInt(rs.getString("RoomNumber"));
                roomList.add(new Room(room_num, room_price, room_type, room_status));
                room.add(new Room(room_num, room_price, room_type, room_status));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to search for rooms based on user input.
    private void Search(ObservableList<Room> room, String s) {
        // Clear the existing room list.
        room.clear();
        // Loop through the room list and add matching rooms to the new list.
        for (int i = 0; i < roomList.size(); i++) {
            if (Integer.toString(roomList.get(i).getNumber()).indexOf(s) == 0) {
                room.add(roomList.get(i));
            }
        }
    }

    // Method to handle the key event for searching rooms.
    public void handleSearchKey(KeyEvent event) {
        // Check if the key event is a key release event.
        if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            // Get the search string from the search text field.
            String s = search.getText();
            // Perform a search based on the search string.
            Search(room, s);
        }
    }
}