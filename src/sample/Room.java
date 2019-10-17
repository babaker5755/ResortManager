package sample;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class Room {

  private String roomNumber;
  private String bedSize;
  private int numBeds;
  private Boolean isVacant;
  private double price;

  public Room(String roomNumber, String bedSize, int numBeds, Boolean isVacant, double price) {
    this.roomNumber = roomNumber;
    this.bedSize = bedSize;
    this.numBeds = numBeds;
    this.isVacant = isVacant;
    this.price = price;
  }

  public String getRoomNumber() {
    return roomNumber;
  }

  public void setRoomNumber(String roomNumber) {
    this.roomNumber = roomNumber;
  }

  public String getBedSize() {
    return bedSize;
  }

  public void setBedSize(String bedSize) {
    this.bedSize = bedSize;
  }

  public int getNumBeds() {
    return numBeds;
  }

  public void setNumBeds(int numBeds) {
    this.numBeds = numBeds;
  }

  public Boolean getVacant() {
    return isVacant;
  }

  public void setVacant(Boolean vacant) {
    isVacant = vacant;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

}
