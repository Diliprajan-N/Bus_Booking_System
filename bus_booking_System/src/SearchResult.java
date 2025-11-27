import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class SearchResult extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTable table;
    DefaultTableModel model;
    JButton bookBtn;
    int userId;
    String from, to, date;

    public SearchResult(int userId, String from, String to, String date) {
        this.userId = userId;
        this.from = from;
        this.to = to;
        this.date = date;

        setTitle("Search Results - Buses");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel label = new JLabel("Available Buses:");
        label.setBounds(20, 10, 200, 25);
        add(label);

        
        model = new DefaultTableModel();
        model.addColumn("Bus ID");
        model.addColumn("Bus Name");
        model.addColumn("From");
        model.addColumn("To");
        model.addColumn("Journey Date");
        model.addColumn("Available Seats");

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 40, 640, 250);
        add(scrollPane);

        
        bookBtn = new JButton("Book Selected Bus");
        bookBtn.setBounds(250, 310, 180, 30);
        add(bookBtn);

        
        loadBusData();

       
        bookBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Select a bus to book!");
                    return;
                }

                int busId = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
                int available = Integer.parseInt(model.getValueAt(selectedRow, 5).toString());

                String seatsStr = JOptionPane.showInputDialog("Enter number of seats to book (Available: " + available + "):");

                try {
                    int seats = Integer.parseInt(seatsStr);
                    if (seats <= 0 || seats > available) {
                        JOptionPane.showMessageDialog(null, "Invalid seat number!");
                        return;
                    }

                    
                    BookingService bookingService = new BookingService();
                    bookingService.bookTicket(userId, busId, seats);

                    JOptionPane.showMessageDialog(null, " Booking successful!");

                    
                    model.setRowCount(0);
                    loadBusData();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Enter valid number of seats.");
                }
            }
        });

        setVisible(true);
    }

    private void loadBusData() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM buses WHERE from_place=? AND to_place=? AND journey_date=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, from);
            ps.setString(2, to);
            ps.setString(3, date);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("bus_name"),
                        rs.getString("from_place"),
                        rs.getString("to_place"),
                        rs.getDate("journey_date"),
                        rs.getInt("available_seats")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

