package com.example.project3;
//When a guest books a room, a Booking object is created using the constructor with the details provided.

public class Booking {
    private int BookingID;
    private int RoomNumber;
    private String GuestName;
    private String CheckinDate;    //The date the guest is scheduled to check in
    private String CheckoutDate;   // Total number of days the room is booked for
    private int TotalDays;
    private int TotalPrice;  // Total price of the booking
    private String Status;

    public Booking(int BookingID,int RoomNumber, String GuestName, String CheckinDate , String CheckoutDate,int TotalDays, int TotalPrice, String Status) {
        this.BookingID = BookingID;
        this.RoomNumber = RoomNumber;
        this.GuestName = GuestName;
        this.CheckinDate = CheckinDate;
        this.CheckoutDate = CheckoutDate;
        this.TotalDays = TotalDays;
        this.TotalPrice = TotalPrice;
        this.Status = Status;
    }   //argumented constructor.

    public String getGuestName() {
        return GuestName;
    }

    public String getStatus() {
        return Status;
    }

    public int getRoomNumber() {
        return RoomNumber;
    }
    public int getTotalDays() {
        return TotalDays;
    }

    public int getTotalPrice() {
        return TotalPrice;
    }

    public int getBookingID() {
        return BookingID;
    }



    public String getCheckinDate() {
        return CheckinDate;
    }
    public String getCheckoutDate() {
        return CheckoutDate;
    }

    public void setGuestName(String GuestName) {
        this.GuestName = GuestName;
    }

    public void setTotalPrice(int TotalPrice) {
        this.TotalPrice = TotalPrice;
    }
    public void setTotalDays(int TotalDays) {
        this.TotalDays = TotalPrice;
    }


    public void setBookingID(int BookingID) {
        this.BookingID = BookingID;
    }



    public void setCheckinDate(String CheckinDate) {
        this.CheckinDate = CheckinDate;
    }

    public void setStatus(String Status) {  //Managing Booking Status: The status of the booking
        // (confirmed, checked-in, cancelled, etc..) can be updated using this method.
        this.Status = Status;
    }

    public void setCheckoutDate(String CheckoutDate) {
        this.CheckoutDate = CheckoutDate;
    }

    public void setRoomNumber(int RoomNumber) {
        this.RoomNumber = RoomNumber;
    }
}
