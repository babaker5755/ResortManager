package sample;

public class Room {

  private String roomName;
  private int numBeds;
  private Boolean isVacant;
  private double price;
  private String imageUrl;

  public Room(
      String roomName,
      int numBeds,
      Boolean isVacant,
      double price,
      String imageUrl) {
    this.roomName = roomName;
    this.numBeds = numBeds;
    this.isVacant = isVacant;
    this.price = price;
    this.imageUrl = "images/" + imageUrl;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public String getRoomName() {
    return roomName;
  }

  public void setRoomName(String roomName) {
    this.roomName = roomName;
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
