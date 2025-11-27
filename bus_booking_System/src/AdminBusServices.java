import java.sql.*;

public class AdminBusServices {

   
    public boolean addBus(String name, String from, String to, String date, int totalSeats, double price) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO buses (bus_name, from_place, to_place, journey_date, total_seat, available_seat, price) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, from);
            ps.setString(3, to);
            ps.setString(4, date);
            ps.setInt(5, totalSeats);
            ps.setInt(6, totalSeats); // initially available seats = total seats
            ps.setDouble(7, price);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean deleteBus(int busId) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "DELETE FROM buses WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, busId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

   
    public String viewBuses() {
        StringBuilder sb = new StringBuilder();
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM buses";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id")).append("\n");
                sb.append("Bus: ").append(rs.getString("bus_name")).append("\n");
                sb.append("From: ").append(rs.getString("from_place")).append("\n");
                sb.append("To: ").append(rs.getString("to_place")).append("\n");
                sb.append("Date: ").append(rs.getString("journey_date")).append("\n");
                sb.append("Seats: ").append(rs.getInt("available_seat")).append("/").append(rs.getInt("total_seat")).append("\n");
                sb.append("Price: ").append(rs.getDouble("price")).append("\n");
                sb.append("------------------------\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
