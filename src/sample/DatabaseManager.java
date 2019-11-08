package sample;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DatabaseManager {
  private Connection conn = null;
  private PreparedStatement ps = null;
  private ResultSet rs = null;
  private boolean isConnectedToDB;

  boolean getConnectionStatus() {
    return isConnectedToDB;
  }

  DatabaseManager() {
    connectToDB();
  }

  private void connectToDB() {

    try {
      // Establish a connection to the database and flag the DBManager as connected.
      Class.forName("org.h2.Driver");
      conn = DriverManager.getConnection("jdbc:h2:./res/Hotel_Database");
      isConnectedToDB = true;

      // Cover cases where a connection could not be made.
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  void disconnectFromDB() {
    try {
      if (rs != null) {
        rs.close();
      }
    } catch (Exception e) {
      System.out.print("Error closing ResultSet.\n");
      e.printStackTrace();
    }
    try {
      if (ps != null) {
        ps.close();
      }
    } catch (Exception e) {
      System.out.print("Error closing PreparedStatement.\n");
      e.printStackTrace();
    }
    try {
      if (conn != null) {
        conn.close();
      }
    } catch (Exception e) {
      System.out.print("Error closing Connection.\n");
      e.printStackTrace();
    }

    isConnectedToDB = false;
    System.out.print("Connection successfully closed.\n");
  }

  void addRoomsToDB(ArrayList<Room> rooms) {

    for (Room room : rooms) {
      System.out.println("Inserting room records into table...");
      try {
        PreparedStatement ps =
            conn.prepareStatement(
                "INSERT INTO ROOMS VALUES ('"
                    + room.getRoomNumber()
                    + "', '"
                    + room.getBedSize()
                    + "', "
                    + room.getNumBeds()
                    + ", "
                    + room.getVacant()
                    + ", "
                    + room.getPrice()
                    + ")");
        ps.executeUpdate();
        System.out.println("Inserted room record into table.");
      } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Could not create room record.");
      }
    }
  }

  // add bookings to database
  void addBookingsToDB(ArrayList<Booking> bookingList) {
    System.out.println("Inserting booking records into table...");
    for (Booking booking : bookingList) {
      try {
        PreparedStatement ps =
                conn.prepareStatement(
                        "INSERT INTO BOOKINGS VALUES ('"
                                + booking.getConfirmationNumber()
                                + "', '"
                                + booking.getRoomNumber()
                                + "', '"
                                + booking.getPrice()
                                + "', '"
                                + booking.getClientName()
                                + "', '"
                                + booking.getClientAddress()
                                + "', '"
                                + booking.getClientCreditCard()
                                + "', '"
                                + booking.getClientEmail()
                                + "', '"
                                + booking.getCheckInDate()
                                + "', '"
                                + booking.getCheckOutDate()
                                + "')");
        ps.executeUpdate();
        System.out.println("Inserted booking record into table.");
      } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Could not create booking record.");
      }
    }
  }

  void addBookingToDB(Booking booking) {
    System.out.println("Inserting booking records into table...");
    try {
      PreparedStatement ps = conn.prepareStatement(
                      "INSERT INTO BOOKINGS VALUES ('"
                              + booking.getConfirmationNumber()
                              + "', '"
                              + booking.getRoomNumber()
                              + "', '"
                              + booking.getPrice()
                              + "', '"
                              + booking.getClientName()
                              + "', '"
                              + booking.getClientAddress()
                              + "', '"
                              + booking.getClientCreditCard()
                              + "', '"
                              + booking.getClientEmail()
                              + "', '"
                              + booking.getCheckInDate()
                              + "', '"
                              + booking.getCheckOutDate()
                              + "')");
      ps.executeUpdate();
      System.out.println("Inserted booking record into table.");
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Could not create booking record.");
    }
  }

  // delete booking from database and ArrayList
  void removeBookingsFromDB(ArrayList<Booking> bookingList, String confirmationNumber) {
    System.out.println("Canceling booking...");
    for (int i = 0; i < bookingList.size()-1; i++) {
      if (bookingList.get(i).getConfirmationNumber().equals(confirmationNumber)) {
        try {
          PreparedStatement ps =
              conn.prepareStatement(
                  "DELETE FROM BOOKINGS WHERE CONFIRMATION_NUMBER='"
                      + confirmationNumber
                      + "'");
          ps.executeUpdate();
          System.out.println("Canceled booking.");
        } catch (SQLException e) {
          e.printStackTrace();
          System.out.println("Could not cancel booking.");
        }
      }
    }
  }

  ArrayList<Room> getRoomsAsList() {
    ArrayList<Room> rooms = new ArrayList<>();

    try {
      // Get all rows from the specified table.
      ps = conn.prepareStatement("SELECT * FROM ROOMS");
      rs = ps.executeQuery();

      // Make each row a room object, then add it to the list of rooms.
      while (rs.next()) {
        Room room =
            new Room(
                rs.getString("ROOM_NUMBER"),
                rs.getString("BED_SIZE"),
                rs.getInt("NUM_BEDS"),
                rs.getBoolean("IS_VACANT"),
                rs.getDouble("PRICE"));
        rooms.add(room);

        // Cumbersome console confirmation.
        System.out.println(
            "ROOM_NUMBER: "
                + rs.getString("ROOM_NUMBER")
                + ", BED_SIZE: "
                + rs.getString("BED_SIZE")
                + ", NUM_BEDS: "
                + rs.getInt("NUM_BEDS")
                + ", IS_VACANT: "
                + rs.getBoolean("IS_VACANT")
                + ", PRICE: "
                + rs.getDouble("PRICE"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.print("Could not execute query.");
    }

    return rooms;
  }

  ArrayList<Booking> getBookingsAsList() {
    ArrayList<Booking> bookingList = new ArrayList<>();

    try {
      // Get all rows from the specified table.
      ps = conn.prepareStatement("SELECT * FROM BOOKINGS");
      rs = ps.executeQuery();

      // Make each row a Booking object, then add it to the list of bookings.
      while (rs.next()) {
        Booking booking =
            new Booking(
                rs.getString("CONFIRMATION_NUMBER"),
                rs.getString("ROOM_NUMBER"),
                rs.getDouble("PRICE"),
                rs.getString("CLIENT_NAME"),
                rs.getString("CLIENT_ADDRESS"),
                rs.getString("CLIENT_CREDITCARD"),
                rs.getString("CLIENT_EMAIL"),
                rs.getDate("START_DATE"),
                rs.getDate("END_DATE"));
        bookingList.add(booking);

        // Confirm in console.
        System.out.println(
            "CONFIRMATION_NUMBER: "
                + rs.getString("CONFIRMATION_NUMBER")
                + ", ROOM_NUMBER: "
                + rs.getString("ROOM_NUMBER")
                + ", PRICE: "
                + rs.getDouble("PRICE")
                + ", CLIENT_NAME: "
                + rs.getString("CLIENT_NAME")
                + ", CLIENT_ADDRESS: "
                + rs.getString("CLIENT_ADDRESS")
                + ", CLIENT_CREDITCARD: "
                + rs.getString("CLIENT_CREDITCARD")
                + ", CLIENT_EMAIL: "
                + rs.getString("CLIENT_EMAIL")
                + ", START_DATE: "
                + rs.getDate("START_DATE")
                + ", END_DATE: "
                + rs.getDate("END_DATE"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.print("Could not execute query.");
    }

    return bookingList;
  }

  ArrayList<Booking> getBookingsByRoom(String roomNumber) {
    ArrayList<Booking> bookingList = new ArrayList<>();

    try {

      // Get all rows from the specified table.
      ps = conn.prepareStatement("SELECT * FROM BOOKINGS WHERE ROOM_NUMBER='"
      + roomNumber
      + "'");
      rs = ps.executeQuery();

      // Make each row a Booking object, then add it to the list of bookings.
      while (rs.next()) {
        Booking booking =
                new Booking(
                        rs.getString("CONFIRMATION_NUMBER"),
                        rs.getString("ROOM_NUMBER"),
                        rs.getDouble("PRICE"),
                        rs.getString("CLIENT_NAME"),
                        rs.getString("CLIENT_ADDRESS"),
                        rs.getString("CLIENT_CREDITCARD"),
                        rs.getString("CLIENT_EMAIL"),
                        rs.getDate("START_DATE"),
                        rs.getDate("END_DATE"));
        bookingList.add(booking);

        // Confirm in console.
        System.out.println(
                "CONFIRMATION_NUMBER: "
                        + rs.getString("CONFIRMATION_NUMBER")
                        + ", ROOM_NUMBER: "
                        + rs.getString("ROOM_NUMBER")
                        + ", PRICE: "
                        + rs.getDouble("PRICE")
                        + ", CLIENT_NAME: "
                        + rs.getString("CLIENT_NAME")
                        + ", CLIENT_ADDRESS: "
                        + rs.getString("CLIENT_ADDRESS")
                        + ", CLIENT_CREDITCARD: "
                        + rs.getString("CLIENT_CREDITCARD")
                        + ", CLIENT_EMAIL: "
                        + rs.getString("CLIENT_EMAIL")
                        + ", START_DATE: "
                        + rs.getDate("START_DATE")
                        + ", END_DATE: "
                        + rs.getDate("END_DATE"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.print("Could not execute query.");
    }

    return bookingList;
  }

  boolean authenticateUser(String userName, String password) {

    boolean authenticatedFlag = false;

    try {
      // Get all rows from the specified table.
      ps = conn.prepareStatement(
              "SELECT USERNAME, PASSWORD FROM MANAGERS WHERE USERNAME='"
                      + userName
                      + "'");
      rs = ps.executeQuery();

      if (rs.next()) {
        if (rs.getString("PASSWORD").equals(password)) {
          authenticatedFlag = true;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.print("Could not execute query.");
    }

    return authenticatedFlag;
  }
}
