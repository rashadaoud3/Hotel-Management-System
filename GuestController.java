package com.example.project3;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GuestController implements Initializable {

    public static int selectedRoomNumber;

    @FXML
    private TextField email;

    @FXML
    private TextField gender;

    @FXML
    private TextField inDate;

    @FXML
    private TextField name;

    @FXML
    private TextField nationality;

    @FXML
    private TextField outDate;

    @FXML
    private TextField phone;

    @FXML
    private TextField price;

    private Connection connection;

    private DBConnection dbConnection;

    private PreparedStatement pst;

    public static void setSelectedRoomNumber(int selectedRoomNumber) {
        GuestController.selectedRoomNumber = selectedRoomNumber;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Creat a new Connection with Data Base
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
        //After We Select the Room Number
        //Check if the Room is not =0
        if (selectedRoomNumber != 0) {
            /*
             * guest information along with their check-in and check-out dates and calculates the total cost of their stay based on the room price and duration of stay, then get information  by a specific room number.
             * */
            String query = "SELECT c.*, res.CheckinDate, res.CheckoutDate, (r.Ppice * DATEDIFF(res.CheckoutDate, res.CheckinDate)) AS Total FROM guest c \n" +
                    "INNER JOIN booking  res ON c.GuestID = res.GuestID\n" +
                    "INNER JOIN room r ON r.RoomNumber = res.RoomNumber\n" +
                    "WHERE r.RoomNumber=?";
            try {
                pst = connection.prepareStatement(query);
                pst.setString(1, Integer.toString(selectedRoomNumber));
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    price.setText(rs.getString("Total"));
                    name.setText(rs.getString("GuestName"));
                    email.setText(rs.getString("Email"));
                    phone.setText(rs.getString("Phone"));
                    gender.setText(rs.getString("Gender"));
                    nationality.setText(rs.getString("Nationaility"));
                    inDate.setText(rs.getString("CheckinDate"));
                    outDate.setText(rs.getString("CheckoutDate"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //this statement to making them read-only.
            price.setEditable(false);
            name.setEditable(false);
            email.setEditable(false);
            phone.setEditable(false);
            gender.setEditable(false);
            nationality.setEditable(false);
            inDate.setEditable(false);
            outDate.setEditable(false);
        }
    }
}