import javax.swing.*;
import java.awt.event.*;

public class HomeScreen extends JFrame {
    private static final long serialVersionUID = 1L;
    JButton searchBusBtn, bookBusBtn, logoutBtn;
    BusService busService = new BusService();
    String username;

    public HomeScreen(String username) {
        this.username = username;

        setTitle("User Dashboard - " + username);
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel title = new JLabel("Welcome, " + username);
        title.setBounds(130, 20, 200, 25);
        add(title);

        searchBusBtn = new JButton("Search Buses");
        searchBusBtn.setBounds(120, 60, 150, 30);
        add(searchBusBtn);

        bookBusBtn = new JButton("Book a Bus");
        bookBusBtn.setBounds(120, 100, 150, 30);
        add(bookBusBtn);

        logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(120, 140, 150, 30);
        add(logoutBtn);

       
        searchBusBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busService.searchBus();
            }
        });

       
        bookBusBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busService.bookBus(username);
            }
        });

        
        logoutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginForm();
            }
        });

        setVisible(true);
    }
}
