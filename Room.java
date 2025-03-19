package com.example.project3;

// A class representing a room in a hotel.

public class Room {
    // Private fields to store room details.
    private int number; // Room number.
    private int price; // Room price.
    private String type; // Room type .
    private String status; // Room status ( booked, not booked).

    // Constructor to initialize room details.
    public Room(int roomNumber, int price, String roomType, String status) {
        this.number = roomNumber;
        this.price = price;
        this.type = roomType;
        this.status = status;
    }

    // Getter methods to retrieve room details.
    public int getNumber() {
        return number;
    }

    public int getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    // Setter methods to modify room details.
    public void setNumber(int number) {
        this.number = number;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }
}
