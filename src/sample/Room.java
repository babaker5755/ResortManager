package sample;

public class Room {

  private String roomNumber;
  private int numBeds;
  private Boolean isVacant;
  private double price;
  private String imageUrl;

  public Room(
      String roomNumber,
      int numBeds,
      Boolean isVacant,
      double price,
      String imageUrl) {
    this.roomNumber = roomNumber;
    this.numBeds = numBeds;
    this.isVacant = isVacant;
    this.price = price;
    this.imageUrl = imageUrl;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public String getRoomNumber() {
    return roomNumber;
  }

  public void setRoomNumber(String roomNumber) {
    this.roomNumber = roomNumber;
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
