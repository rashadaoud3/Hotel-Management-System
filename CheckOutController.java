package com.example.project3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

// This class controls the functionality of the CheckOut screen.
public class CheckOutController implements Initializable {

    @FXML
    private TableColumn<Booking, String> checkIn; // Table column for check-in date.

    @FXML
    private TableColumn<Booking, String> checkOut; // Table column for check-out date.

    @FXML
    private TableColumn<Booking, String> customerName; // Table column for customer name.

    @FXML
    private TableColumn<Booking, String> roomNumber; // Table column for room number.

    @FXML
    private TableView<Booking> roomTable; // Table view for displaying bookings.

    @FXML
    private TextField search; // Text field for searching by room number.

    @FXML
    private Button today; // Button for filtering reservations for today.

    @FXML
    private TableColumn<Booking, String> totalDays; // Table column for total days of stay.

    @FXML
    private TableColumn<Booking, String> totalPrice; // Table column for total price.

    @FXML
    private TableColumn<?, ?> status; // Table column for reservation status.

    @FXML
    private Button unspecified; // Button for unspecified filtering.

    @FXML
    private ComboBox<String> sort; // Combo box for sorting reservations.

    private Connection connection; // Database connection.

    private DBConnection dbConnection; // Database connection utility class.

    private PreparedStatement pst; // Prepared statement for executing SQL queries.

    // Observable list for storing reservations displayed in the table view.
    public static final ObservableList<Booking> reservations  = FXCollections.observableArrayList();

    // List for storing all reservations fetched from the database.
    public static final List<Booking> reservationList = new ArrayList<>();

    // Initializes the controller class.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize database connection.
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();

        // Initialize the sorting combo box options.
        sort.getItems().removeAll(sort.getItems());
        sort.getItems().addAll("Today", "Checked In", "Checked Out");

        // Initialize table columns with corresponding property values.
        roomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("GuestName"));
        checkIn.setCellValueFactory(new PropertyValueFactory<>("CheckinDate"));
        checkOut.setCellValueFactory(new PropertyValueFactory<>("CheckoutDate"));
        totalDays.setCellValueFactory(new PropertyValueFactory<>("TotalDays"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<>("TotalPrice"));
        status.setCellValueFactory(new PropertyValueFactory<>("Status"));

        // Load reservations data into the table.
        try {
            initReservationList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        roomTable.setItems(reservations);
    }

    // Retrieves reservations data from the database and populates the reservation list.
    public void initReservationList() throws IOException {
        reservationList.clear();
        reservations.clear();
        String query = "SELECT res.Status, res.BookingID, res.RoomNumber, c.GuestName, res.CheckinDate, res.CheckoutDate, DATEDIFF(res.CheckoutDate, res.CheckinDate) AS totalDays, (r.Ppice * DATEDIFF(res.CheckoutDate, res.CheckinDate)) AS totalPrice FROM guest c\n" +
                "INNER JOIN booking res ON c.GuestID = res.GuestID\n" +
                "INNER JOIN room r ON r.RoomNumber = res.RoomNumber\n";
        try {
            pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int res_id = Integer.parseInt(rs.getString("BookingID"));
                int room_no = Integer.parseInt(rs.getString("RoomNumber"));
                String cus_name = rs.getString("GuestName");
                String check_in = rs.getString("CheckinDate");
                String check_out = rs.getString("CheckoutDate");
                int total_price = Integer.parseInt(rs.getString("totalPrice"));
                int total_days = Integer.parseInt(rs.getString("totalDays"));
                String res_status = rs.getString("Status");
                reservationList.add(new Booking(res_id, room_no, cus_name, check_in, check_out,  total_days, total_price,res_status));
                reservations.add(new Booking(res_id, room_no, cus_name, check_in, check_out,  total_days, total_price,res_status));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Searches for reservations by room number.
    private void searchByRoomNumber(ObservableList<Booking> res, String s) {
        res.clear();
        for (int i = 0; i < reservationList.size(); i++) {
            if (Integer.toString(reservationList.get(i).getRoomNumber()).indexOf(s) == 0) {
                res.add(reservationList.get(i));
            }
        }
    }

    // Handles search by room number when a key is released in the search text field.
    public void handleSearchKey(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            String s = search.getText();
            searchByRoomNumber(reservations, s);
        }
    }

    // Handles checkout button action.
    public void handleCheckoutButton(javafx.event.ActionEvent actionEvent) {

    }

    // Updates the table view with the latest data after a reservation status change.
    public void updateTable(Booking x) {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).equals(x)) {
                reservations.get(i).setStatus("Checked Out");
            }
        }
        roomTable.setItems(reservations);
    }

    // Handles double-clicking on a table row to view reservation details.
    public void clickItem(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            if (roomTable.getSelectionModel().getSelectedItem() != null) {
                Booking selectedReservation = roomTable.getSelectionModel().getSelectedItem();
                PaymentnfoController.setSelectedReservationID(selectedReservation.getBookingID());
                PaymentnfoController.setSelectedReservation(selectedReservation);
                Stage add = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("billinfo.fxml"));
                Scene scene = new Scene(root);
                add.setScene(scene);
                add.show();
            }
        }
    }

    // Handles selection change in the sorting combo box.
    public void handleComboboxSelection(javafx.event.ActionEvent actionEvent) {
        if (sort.getSelectionModel().getSelectedItem().equals("Today")) {
            reservations.clear();
            for (int i = 0; i < reservationList.size(); i++) {
                if (reservationList.get(i).getCheckoutDate().equals(java.time.LocalDate.now().toString()) &&
                        reservationList.get(i).getStatus().equals("Checked In")) {
                    reservations.add(reservationList.get(i));
                }
            }
        } else if (sort.getSelectionModel().getSelectedItem().equals("Checked In")) {
            reservations.clear();
            for (int i = 0; i < reservationList.size(); i++) {
                if (reservationList.get(i).getStatus().equals("Checked In")) {
                    reservations.add(reservationList.get(i));
                }
            }
        } else if (sort.getSelectionModel().getSelectedItem().equals("Checked Out")) {
            reservations.clear();
            for (int i = 0; i < reservationList.size(); i++) {
                if (reservationList.get(i).getStatus().equals("Checked Out")) {
                    reservations.add(reservationList.get(i));
                }
            }
        }
    }
}