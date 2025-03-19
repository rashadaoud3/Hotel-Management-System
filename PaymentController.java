package com.example.project3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
/*This Class To Handle the pymentController Page By :Initializable interface
 * Remark : instances of this class will be used to initialize the JavaFX components defined in an FXML file (We Ues an The SceneBuilder.
 *  */
public class PaymentController implements Initializable {

    @FXML
    private TableColumn<Payment, String> Amount;

    @FXML
    private TableColumn<Payment, String> Date;

    @FXML
    private TableColumn<Payment, String> billID;

    @FXML
    private TableColumn<Payment, String> cusIDNumber;

    @FXML
    private TableColumn<Payment, String> customerName;

    @FXML
    private TableColumn<Payment, String> roomNumber;

    @FXML
    private TableView<Payment> billTable;

    @FXML
    private TextField search;

    private Connection connection;

    private DBConnection dbConnection;

    private PreparedStatement pst;

    public static final ObservableList<Payment> bills = FXCollections.observableArrayList();

    public static final List<Payment> billList = new ArrayList<>();//Dynamic Array of list

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
        //specific column in a JavaFX TableView to display data from an associated property in the Payment class.
        roomNumber.setCellValueFactory(new PropertyValueFactory<>("RoomNumber"));
        cusIDNumber.setCellValueFactory(new PropertyValueFactory<>("GuestID"));
        billID.setCellValueFactory(new PropertyValueFactory<>("billID"));
        Amount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("GuestName"));
        try {
            initBillList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        billTable.setItems(bills);
    }

    public void initBillList() throws IOException {
        billList.clear();
        bills.clear();
        /*
         * trieves details such as payment information, room numbers, guest IDs, and guest names, effectively consolidating data across these tables based on their relationships.
         */

        String query = "SELECT b.*, res.RoomNumber, res.GuestID, c.GuestName FROM payment b\n" +
                "INNER JOIN booking res ON b.BookingID = res.BookingID\n" +
                "INNER JOIN guest c ON res.GuestID = c.GuestID";
        try {
            pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int room_number = Integer.parseInt(rs.getString("RoomNumber"));
                int cus_number = Integer.parseInt(rs.getString("GuestID"));
                int bill_id = Integer.parseInt(rs.getString("PaymentID"));
                String date = rs.getString("date");
                String cus_name = rs.getString("GuestName");
                int bill_amount = Integer.parseInt(rs.getString("Amount"));
                billList.add(new Payment(bill_id, cus_name, cus_number, date, bill_amount, room_number));
                bills.add(new  Payment(bill_id, cus_name, cus_number, date, bill_amount, room_number));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*
     * searches for payment records whose dates start with a specified string within a given list and populates an observable list with the matching records.
     * */
    private void Search(ObservableList< Payment> bills, String s) {
        bills.clear();
        for (int i = 0; i < billList.size(); i++) {
            if (billList.get(i).getDate().indexOf(s) == 0) {
                bills.add(billList.get(i));
            }
        }
    }

    public void handleSearchKey(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            String s = search.getText();
            Search(bills, s);
        }
    }
    //this handle the Action when i Click on any Bill
    public void clickBill(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            if (billTable.getSelectionModel().getSelectedItem() != null) {
                String path = "C:\\Users\\SS\\Desktop\\DataBase\\Project\\ProjectPhase3\\src\\main\\resources";
                Payment selectedBill = billTable.getSelectionModel().getSelectedItem();
                File file = new File(path + "payment" + selectedBill.getBillID() + ".pdf");
                if (file.toString().endsWith(".pdf"))
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file);
                else {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(file);
                }
            }
        }
    }
}