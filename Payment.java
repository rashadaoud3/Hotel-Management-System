package com.example.project3;

public class Payment {
    /*ttributes for a Payment clas*/
    private int PaymentID;
    private String GuestName;
    private int GuestID;
    private String date;
    private int Amount;
    private int RoomNumber;
    /*
     *  this constructor is to initialize a Payment object with specific values for its attributes,
     * */
    public Payment(int PaymentID, String GuestName, int GuestID, String date, int Amount, int RoomNumber) {
        this.PaymentID = PaymentID;
        this.GuestName = GuestName;
        this.GuestID = GuestID;
        this.date = date;
        this.Amount = Amount;
        this.RoomNumber = RoomNumber;
    }
    //returns the value of the GuestName attribute,
    public String getGuestName() {
        return GuestName;
    }
    //returns the value of the getRoomNumber attribute,
    public int getRoomNumber() {
        return RoomNumber;
    }
    //returns the value of the getAmount attribute,
    public int getAmount() {
        return Amount;
    }
    ////returns the value of the getBillID attribute,
    public int getBillID() {
        return PaymentID;
    }
//////returns the value of the getGuestID attribute,

    public int getGuestID() {
        return GuestID;
    }

    public String getDate() {
        return date;
    }
    //in the next is to set the value
    public void setGuestName(String GuestName) {
        this.GuestName = GuestName;
    }

    public void setAmount(int Amount) {
        this.Amount = Amount;
    }

    public void setBillID(int PaymentID) {
        this.PaymentID = PaymentID;
    }

    public void setGuestID(int GuestID) {
        this.GuestID = GuestID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRoomNumber(int RoomNumber) {
        this.RoomNumber = RoomNumber;
    }
}