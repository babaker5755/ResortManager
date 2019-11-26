package sample;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import static sample.DetailedResortSummaryReport.TAX_RATE;

public class Booking {

  private String confirmationNumber;
  private String roomNumber;
  private double price;
  private double charge;
  private String clientName;
  private String clientAddress;
  private String clientCreditCard;
  private String clientEmail;
  private Date checkInDate;
  private Date checkOutDate;

  // Default constructor. Requires every field.
  Booking(
      String confirmationNumber,
      String roomNumber,
      double price,
      String clientName,
      String clientAddress,
      String clientCreditCard,
      String clientEmail,
      Date checkInDate,
      Date checkOutDate) {
    this.confirmationNumber = confirmationNumber;
    this.roomNumber = roomNumber;
    this.price = price;
    this.clientName = clientName;
    this.clientAddress = clientAddress;
    this.clientCreditCard = clientCreditCard;
    this.clientEmail = clientEmail;
    this.checkInDate = new Date(checkInDate.getTime());
    this.checkOutDate = new Date(checkOutDate.getTime());
  }

  public double getCharge() {
    int days = (int) ChronoUnit.DAYS.between(checkInDate.toInstant(),checkOutDate.toInstant());
    this.charge = (this.price * days + TAX_RATE * (this.price * days));
    return charge;
  }

  public String getConfirmationNumber() {
    return confirmationNumber;
  }
  public void setConfirmationNumber(String confirmationNumber) {
    this.confirmationNumber = confirmationNumber;
  }

  public String getRoomNumber() {
    return roomNumber;
  }
  public void setRoomNumber(String roomNumber) {
    this.roomNumber = roomNumber;
  }

  public double getPrice() {
    return price;
  }
  public void setPrice(double price) {
    this.price = price;
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

  public Date getCheckInDate() {
    return new Date(checkInDate.getTime());
  }
  public void setCheckInDate(Date checkInDate) {
    this.checkInDate = new Date(checkInDate.getTime());
  }

  public Date getCheckOutDate() {
    return new Date(checkOutDate.getTime());
  }
  public void setCheckOutDate(Date checkOutDate) {
    this.checkOutDate = new Date(checkOutDate.getTime());
  }
}
