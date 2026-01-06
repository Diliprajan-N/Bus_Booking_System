import java.sql.*;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class BookingService {
    Scanner sc = new Scanner(System.in);

    public void bookTicket(int userId) {	
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter Bus ID: ");
            int busId = sc.nextInt();

            System.out.print("Enter number of seats to book: ");
            int seats = sc.nextInt();

            
            String checkQuery = "SELECT available_seats FROM buses WHERE id=?";
            PreparedStatement checkPs = conn.prepareStatement(checkQuery);
            checkPs.setInt(1, busId);

            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                int available = rs.getInt("available_seats");

                if (available >= seats) {
                   
                    String updateSeats = "UPDATE buses SET available_seats=? WHERE id=?";
                    PreparedStatement updatePs = conn.prepareStatement(updateSeats);
                    updatePs.setInt(1, available - seats);
                    updatePs.setInt(2, busId);
                    updatePs.executeUpdate();

                   
                    String insertBooking = "INSERT INTO bookings (user_id, bus_id, seats_booked) VALUES (?, ?, ?)";
                    PreparedStatement bookPs = conn.prepareStatement(insertBooking);
                    bookPs.setInt(1, userId);
                    bookPs.setInt(2, busId);
                    bookPs.setInt(3, seats);
                    bookPs.executeUpdate();

                    System.out.println(" Booking successful!");
                } else {
                    System.out.println(" Not enough seats available.");
                }
            } else {
                System.out.println(" Bus not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewBookings(int userId) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT b.id, bu.bus_name, bu.from_place, bu.to_place, bu.journey_date, b.seats_booked, b.booking_date " +
                           "FROM bookings b JOIN buses bu ON b.busid = bu.id WHERE b.userid = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n=== My Bookings ===");
            while (rs.next()) {
                System.out.println("Booking ID: " + rs.getInt("id"));
                System.out.println("Bus Name: " + rs.getString("bus_name"));
                System.out.println("From: " + rs.getString("from_place"));
                System.out.println("To: " + rs.getString("to_place"));
                System.out.println("Journey Date: " + rs.getDate("journey_date"));
                System.out.println("Seats Booked: " + rs.getInt("seats_booked"));
                System.out.println("Booking Date: " + rs.getTimestamp("booking_date"));
                System.out.println("--------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void bookTicket(int userId, int busId, int seats) {
        try (Connection conn = DBConnection.getConnection()) {
            
            String checkQuery = "SELECT available_seats FROM buses WHERE id=?";
            PreparedStatement checkPs = conn.prepareStatement(checkQuery);
            checkPs.setInt(1, busId);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                int available = rs.getInt("available_seats");

                if (available >= seats) {
                  
                    String updateSeats = "UPDATE buses SET available_seats=? WHERE id=?";
                    PreparedStatement updatePs = conn.prepareStatement(updateSeats);
                    updatePs.setInt(1, available - seats);
                    updatePs.setInt(2, busId);
                    updatePs.executeUpdate();

                 
                    String insertBooking = "INSERT INTO bookings (user_id, bus_id, seats_booked) VALUES (?, ?, ?)";
                    PreparedStatement insertPs = conn.prepareStatement(insertBooking);
                    insertPs.setInt(1, userId);
                    insertPs.setInt(2, busId);
                    insertPs.setInt(3, seats);
                    insertPs.executeUpdate();

                } else {
                    JOptionPane.showMessageDialog(null, "❌ Not enough seats available.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "❌ Bus not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

