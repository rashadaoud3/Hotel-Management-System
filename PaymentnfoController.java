package com.example.project3;


import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.project3.RoomController.roomList;
import static com.example.project3.RoomController.room;

public class PaymentnfoController implements Initializable {

    // Static variables to hold the selected reservation details
    public static int selectedResID;
    public static Booking selectedReservation;

    // FXML annotations to bind UI components to JavaFX
    @FXML
    private TextField Amount;
    @FXML
    private Button print;
    @FXML
    private Button delete;
    @FXML
    private TextField customerIDNumber;
    @FXML
    private TextField customerName;
    @FXML
    private TextField roomNumber;

    // Database connection related fields
    private Connection connection;
    private DBConnection dbConnection;
    private PreparedStatement pst;

    // Static methods to set selected reservation details
    public static void setSelectedReservationID(int selectedReservationID) {
        selectedResID = selectedReservationID;
    }

    public static void setSelectedReservation(Booking reservation) {
        selectedReservation = reservation;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize database connection
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();

        // Populate fields if a reservation is selected
        if (selectedResID != 0) {
            String query = "SELECT res.BookingID, res.RoomNumber, c.GuestID, c.GuestName, (r.Ppice * DATEDIFF(res.CheckoutDate, res.CheckinDate)) AS totalPrice " +
                    "FROM guest c INNER JOIN booking res ON c.GuestID = res.GuestID " +
                    "INNER JOIN room r ON r.RoomNumber = res.RoomNumber " +
                    "WHERE res.BookingID=?";
            try {
                pst = connection.prepareStatement(query);
                pst.setString(1, Integer.toString(selectedResID));
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    roomNumber.setText(rs.getString("RoomNumber"));
                    customerIDNumber.setText(rs.getString("GuestID"));
                    customerName.setText(rs.getString("GuestName"));
                    Amount.setText(rs.getString("totalPrice"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            roomNumber.setEditable(false);
            customerIDNumber.setEditable(false);
            customerName.setEditable(false);
            Amount.setEditable(false);
        }
    }

    // Event handler for delete button action
    public void handleDeleteAction(javafx.event.ActionEvent actionEvent) throws IOException {
        String query = "DELETE FROM guest WHERE GuestID = ?";
        if (!selectedReservation.getStatus().equals("Checked In")) {
            try {
                pst = connection.prepareStatement(query);
                pst.setString(1, customerIDNumber.getText());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Event handler for print button action
    public void handlePrintAction(javafx.event.ActionEvent actionEvent) throws IOException {
        String id = "";
        String insertBills = "INSERT INTO payment (BookingID, date, Amount) VALUES (?, ?, ?)";
        String updateRoom = "UPDATE room SET Status=\"Not Booked\" WHERE RoomNumber=?";
        String updateReservation = "UPDATE booking SET Status=\"Checked Out\" WHERE BookingID=?";
        String selectBill = "SELECT PaymentID FROM payment WHERE BookingID=?";

        if (!selectedReservation.getStatus().equals("Checked Out")) {
            try {
                pst = connection.prepareStatement(insertBills);
                pst.setString(1, String.valueOf(selectedReservation.getBookingID()));
                pst.setString(2, selectedReservation.getCheckoutDate());
                pst.setString(3, String.valueOf(selectedReservation.getTotalPrice()));
                pst.executeUpdate();

                pst = connection.prepareStatement(updateRoom);
                pst.setString(1, String.valueOf(selectedReservation.getRoomNumber()));
                pst.executeUpdate();

                pst = connection.prepareStatement(updateReservation);
                pst.setString(1, String.valueOf(selectedReservation.getBookingID()));
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            pst = connection.prepareStatement(selectBill);
            pst.setString(1, String.valueOf(selectedReservation.getBookingID()));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                id = rs.getString("PaymentID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(id);
        createBill(id);
    }

    // Method to create a PDF bill
    private void createBill(String id) throws IOException {
        String billID = "";
        String customerName = "";
        String customerIDNumber = "";
        String customerPhoneNo = "";
        String roomNumber = "";
        String roomType = "";
        String priceRoom = "";
        String checkIn = "";
        String checkOut = "";
        String totalDay = "";
        String totalPrice = "";
        String path = "C:\\Users\\SS\\Desktop\\DataBase\\Project\\project3\\res\\";

        String billQuery = "SELECT b.PaymentID, c.GuestID, c.GuestName, c.Phone, r.RoomNumber, r.Type, r.Ppice, res.CheckinDate, res.CheckoutDate, " +
                "(r.Ppice * DATEDIFF(res.CheckoutDate, res.CheckinDate)) AS totalPrice, DATEDIFF(res.CheckoutDate, res.CheckinDate) AS totalDay " +
                "FROM payment b INNER JOIN booking res ON b.BookingID = res.BookingID " +
                "INNER JOIN room r ON r.RoomNumber = res.RoomNumber " +
                "INNER JOIN guest c ON c.GuestID = res.GuestID " +
                "WHERE b.PaymentID=?";
        try {
            pst = connection.prepareStatement(billQuery);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                billID = rs.getString("PaymentID");
                customerName = rs.getString("GuestName");
                customerIDNumber = rs.getString("GuestID");
                customerPhoneNo = rs.getString("Phone");
                roomNumber = rs.getString("RoomNumber");
                roomType = rs.getString("Type");
                priceRoom = rs.getString("Ppice");
                checkIn = rs.getString("CheckinDate");
                checkOut = rs.getString("CheckoutDate");
                totalDay = rs.getString("totalDay");
                totalPrice = rs.getString("totalPrice");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Create PDF document
        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(path + "Bill" + id + ".pdf"));
            doc.open();
            Paragraph paragraph1 = new Paragraph("Bill ID: " + billID + "\nGuest Details:\n Guest Name: " + customerName + "\nGuest Number: " + customerIDNumber +
                    "\nMobile Number: " + customerPhoneNo + "\n");
            doc.add(paragraph1);
            Paragraph paragraph2 = new Paragraph("\nRoom Details:\nRoom Number: " + roomNumber + "\nRoom Type: " + roomType +
                    "\nPrice Per Day: " + priceRoom + "\n" + "\n");
            doc.add(paragraph2);
            PdfPTable table = new PdfPTable(4);
            table.addCell("Check In Date: " + checkIn);
            table.addCell("Check Out Date: " + checkOut);
            table.addCell("Number of Days Stay: " + totalDay);
            table.addCell("Total Amount Paid: " + totalPrice);
            doc.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
        doc.close();

        // Open the PDF file
        File file = new File(path + "payment" + id + ".pdf");
        if (file.toString().endsWith(".pdf"))
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file);
        else {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        }
    }
}
