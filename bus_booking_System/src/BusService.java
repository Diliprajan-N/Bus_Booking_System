import java.sql.*;
import javax.swing.*;

public class BusService {

  
    public void searchBus() {
        try (Connection conn = DBConnection.getConnection()) {
            String from = JOptionPane.showInputDialog("From:");
            String to = JOptionPane.showInputDialog("To:");
            String date = JOptionPane.showInputDialog("Journey Date (YYYY-MM-DD):");

            String query = "SELECT * FROM buses WHERE from_place=? AND to_place=? AND journey_date=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, from);
            ps.setString(2, to);
            ps.setString(3, date);

            ResultSet rs = ps.executeQuery();
            StringBuilder sb = new StringBuilder();

            while (rs.next()) {
                sb.append("Bus ID: ").append(rs.getInt("id")).append("\n");
                sb.append("Bus Name: ").append(rs.getString("bus_name")).append("\n");
                sb.append("Seats Available: ").append(rs.getInt("available_seat")).append("\n");
                sb.append("Price: ").append(rs.getDouble("price")).append("\n");
                sb.append("--------------------------\n");
            }

            JOptionPane.showMessageDialog(null, sb.toString(), "Available Buses", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  
    public void bookBus(String username) {
        try (Connection conn = DBConnection.getConnection()) {
            int busId = Integer.parseInt(JOptionPane.showInputDialog("Enter Bus ID to book:"));
            int seats = Integer.parseInt(JOptionPane.showInputDialog("How many seats?"));

            
            String getUser = "SELECT id FROM users WHERE username=?";
            PreparedStatement psUser = conn.prepareStatement(getUser);
            psUser.setString(1, username);
            ResultSet rsUser = psUser.executeQuery();
            if (!rsUser.next()) {
                JOptionPane.showMessageDialog(null, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int userId = rsUser.getInt("id");

        
            String checkBus = "SELECT available_seat, price FROM buses WHERE id=?";
            PreparedStatement psBus = conn.prepareStatement(checkBus);
            psBus.setInt(1, busId);
            ResultSet rsBus = psBus.executeQuery();

            if (rsBus.next()) {
                int available = rsBus.getInt("available_seat");
                double price = rsBus.getDouble("price");

                if (seats > available) {
                    JOptionPane.showMessageDialog(null, "Not enough seats available!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

              
                String updateBus = "UPDATE buses SET available_seat=available_seat-? WHERE id=?";
                PreparedStatement psUpdate = conn.prepareStatement(updateBus);
                psUpdate.setInt(1, seats);
                psUpdate.setInt(2, busId);
                psUpdate.executeUpdate();

              
                double totalFare = seats * price;
                String insertBooking = "INSERT INTO booking (user_id, bus_id, seats_booked, total_fare) VALUES (?, ?, ?, ?)";
                PreparedStatement psBooking = conn.prepareStatement(insertBooking);
                psBooking.setInt(1, userId);
                psBooking.setInt(2, busId);
                psBooking.setInt(3, seats);
                psBooking.setDouble(4, totalFare);
                psBooking.executeUpdate();

                JOptionPane.showMessageDialog(null, "Booking Successful! Total Fare: " + totalFare);
            } else {
                JOptionPane.showMessageDialog(null, "Bus not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
