package sample;

import java.util.ArrayList;

public class BookingManager {

  /**
   * Holds a collection of Booking objects.
   */
  private ArrayList<Booking> bookingList;

  /**
   * Default constructor. Populates the booking manager with all bookings in the database.
   * Represents every booking ever made in the system.
   */
  BookingManager() {
    bookingList = new ArrayList<>();
    DatabaseManager DBM = new DatabaseManager();
    bookingList = DBM.getBookingsAsList();
    DBM.disconnectFromDB();
  }

  /**
   * Overloaded constructor. Populates the booking manager with the ArrayList argument.
   * @param bookingList A list of bookings to make into a booking manager.
   */
  BookingManager(ArrayList<Booking> bookingList) {
    this.bookingList = bookingList;
  }

  /**
   * Adds a booking to the booking manager.
   * @param myBooking The booking to add to the manager.
   */
  public void addBooking(Booking myBooking) {
    bookingList.add(myBooking);
  }

  /**
   * Adds a booking to the booking manager.
   * @param confirmationNumber The confirmation number for the booking to remove from the manager.
   */
  public void removeBooking(String confirmationNumber) {
    for (int i = 0; i < bookingList.size(); i++) {
      if (bookingList.get(i).getConfirmationNumber().equals(confirmationNumber)) {
        bookingList.remove(i);
      }
    }
  }

  /**
   * Populates the list of bookings from the database.
   */
  public void populateFromDB() {
    DatabaseManager DBM = new DatabaseManager();
    bookingList = DBM.getBookingsAsList();
    DBM.disconnectFromDB();
  }

  /**
   * Returns the list of bookings owned by the booking manager.
   * @return ArrayList A list of all bookings in the booking manager.
   */
  public ArrayList<Booking> getBookingList() {
    return bookingList;
  }
}
