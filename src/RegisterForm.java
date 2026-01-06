import javax.swing.*;
import java.awt.event.*;

public class RegisterForm extends JFrame {
    private static final long serialVersionUID = 1L;
    JTextField usernameField;
    JPasswordField passwordField;
    JButton registerBtn, backBtn;
    UserService userService = new UserService();

    public RegisterForm() {
        setTitle("Bus Booking - Register");
        setSize(550, 450);
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

        registerBtn = new JButton("Register");
        registerBtn.setBounds(40, 110, 100, 25);
        add(registerBtn);

        backBtn = new JButton("Back");
        backBtn.setBounds(160, 110, 100, 25);
        add(backBtn);

        
        registerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                boolean success = userService.register(username, password);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Registration Successful!");
                    dispose();
                    new LoginForm();
                } else {
                    JOptionPane.showMessageDialog(null, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        backBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginForm();
            }
        });

        setVisible(true);
    }
}
