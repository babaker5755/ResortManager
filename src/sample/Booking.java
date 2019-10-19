package sample;

import java.time.LocalDate;
import java.util.Date;

public class Booking {

  private Room room;
  private String clientName;
  private String clientAddress;
  private String clientCreditCard;
  private String clientEmail;
  private String confirmationNumber;
  private LocalDate checkInDate;
  private LocalDate checkOutDate;

  public Booking(Room room, String clientName, String clientAddress, String clientCreditCard,
                 String clientEmail, LocalDate checkInDate, LocalDate checkOutDate, String confirmationNumber){
    this.room = room;
    this.clientName = clientName;
    this.clientAddress = clientAddress;
    this.clientCreditCard = clientCreditCard;
    this.clientEmail = clientEmail;
    this.confirmationNumber = confirmationNumber;
    this.checkInDate = checkInDate;
    this.checkOutDate = checkOutDate;
    this.room.setVacant(false);
  }

  public String getClientName() {
    return clientName;
  }
  public void setClientName(String clientName) {
    this.clientName = clientName;
  }

  public String getClientAddress() {
    return clientAddress;
  }
  public void setClientAddress(String clientAddress) {
    this.clientAddress = clientAddress;
  }

  public String getClientCreditCard() {
    return clientCreditCard;
  }
  public void setClientCreditCard(String clientCreditCard) {
    this.clientCreditCard = clientCreditCard;
  }

  public String getClientEmail() {
    return clientEmail;
  }
  public void setClientEmail(String clientEmail) {
    this.clientEmail = clientEmail;
  }

  public String getRoomNumber() { return room.getRoomNumber(); }
  public void setRoomNumber(String roomNumber) { room.setRoomNumber(roomNumber); }

  public String getConfirmationNumber() {
    return confirmationNumber;
  }
  public void setConfirmationNumber(String confirmationNumber) {
    this.confirmationNumber = confirmationNumber;
  }

  public double getPrice() { return room.getPrice(); }
  public void setPrice(double price) { room.setPrice(price); }

  public LocalDate getCheckInDate() { return checkInDate; }
  public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }

  public LocalDate getCheckOutDate() { return checkOutDate; }
  public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }

}
