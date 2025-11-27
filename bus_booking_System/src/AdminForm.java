import javax.swing.*;
import java.awt.event.*;

public class AdminForm extends JFrame {
    private static final long serialVersionUID = 1L;

    JButton addBusBtn, deleteBusBtn, viewBusBtn, logoutBtn;
    AdminBusServices adminService = new AdminBusServices();

    public AdminForm() {
        setTitle("Admin Dashboard - Bus Management");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel title = new JLabel("Admin Dashboard");
        title.setBounds(130, 20, 200, 25);
        add(title);

        addBusBtn = new JButton("Add Bus");
        addBusBtn.setBounds(120, 60, 150, 30);
        add(addBusBtn);

        deleteBusBtn = new JButton("Delete Bus");
        deleteBusBtn.setBounds(120, 100, 150, 30);
        add(deleteBusBtn);

        viewBusBtn = new JButton("View All Buses");
        viewBusBtn.setBounds(120, 140, 150, 30);
        add(viewBusBtn);

        logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(120, 180, 150, 30);
        add(logoutBtn);

        // ✅ Add bus
        addBusBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Bus Name:");
                String from = JOptionPane.showInputDialog("From:");
                String to = JOptionPane.showInputDialog("To:");
                String date = JOptionPane.showInputDialog("Journey Date (YYYY-MM-DD):");
                int totalSeat = Integer.parseInt(JOptionPane.showInputDialog("Total Seats:"));
                double price = Double.parseDouble(JOptionPane.showInputDialog("Ticket Price:"));

                boolean success = adminService.addBus(name, from, to, date, totalSeat, price);
                JOptionPane.showMessageDialog(null, success ? "Bus Added!" : "Failed to add bus!");
            }
        });

        // ✅ Delete bus
        deleteBusBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int busId = Integer.parseInt(JOptionPane.showInputDialog("Enter Bus ID to delete:"));
                boolean success = adminService.deleteBus(busId);
                JOptionPane.showMessageDialog(null, success ? "Bus Deleted!" : "Bus not found!");
            }
        });

        // ✅ View buses
        viewBusBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String buses = adminService.viewBuses();
                JOptionPane.showMessageDialog(null, buses, "All Buses", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // ✅ Logout
        logoutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginForm();
            }
        });

        setVisible(true);
    }
}
