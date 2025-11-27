import javax.swing.*;
import java.awt.event.*;

public class LoginForm extends JFrame {
    private static final long serialVersionUID = 1L;
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginBtn, registerBtn;
    UserService userService = new UserService();

    public LoginForm() {
        setTitle("Bus Booking - Login");
        setSize(650, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 30, 80, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(120, 30, 150, 25);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 70, 80, 25);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 70, 150, 25);
        add(passwordField);

        loginBtn = new JButton("Login");
        loginBtn.setBounds(40, 110, 100, 25);
        add(loginBtn);

        registerBtn = new JButton("Register");
        registerBtn.setBounds(160, 110, 100, 25);
        add(registerBtn);

        // ✅ Login button action
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                String role = userService.login(username, password);

                if (role.equals("admin")) {
                    JOptionPane.showMessageDialog(null, "Welcome Admin!");
                    dispose();
                    new AdminForm(); 
                } else if (role.equals("user")) {
                    JOptionPane.showMessageDialog(null, "Login Success!");
                    dispose();
                    new HomeScreen(username); 
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        registerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RegisterForm(); 
            }
        });

        setVisible(true);
    }
}
