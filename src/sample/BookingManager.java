package sample;

import java.util.ArrayList;

public class BookingManager {

  private ArrayList<Booking> bookingList;

  BookingManager() {
    bookingList = new ArrayList<>();
  }


  // Overloaded constructor.
  BookingManager(ArrayList<Booking> bookingList) {
    this.bookingList = bookingList;
  }

  public void addBooking(Booking myBooking) {
    bookingList.add(myBooking);
  }

  public void removeBooking(String confirmationNumber) {
    for (int i = 0; i < bookingList.size(); i++) {
      if (bookingList.get(i).getConfirmationNumber() == confirmationNumber) {
        bookingList.remove(i);
      }
    }
  }

  public void populateFromDB() {
    DatabaseManager DBM = new DatabaseManager();
    bookingList = DBM.getBookingsAsList();
    DBM.disconnectFromDB();
  }

  public ArrayList<Booking> getBookingList() {
    return bookingList;
  }
}
