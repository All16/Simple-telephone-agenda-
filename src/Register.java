import javax.swing.*;
import java.sql.*;

public class Register extends JFrame {
    /**
     * The register panel
     */
    private JPanel RegisterPanel;
    /**
     * The container panel
     */
    private JPanel Container;

    /**
     * Create a new Register frame with a register panel and a container panel
     * The register panel contains the container panel
     * The container panel contains the username and password fields and the register button
     * The register button checks if the username is already taken, if the password is valid, and adds the user to the database
     */
    public Register() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(390, 844);
        setResizable(false);
        setTitle("Register");


        // Create a login panel with fields for username and password, and a login button and center it
        RegisterPanel.setLayout(null);
        RegisterPanel.setSize(390, 844);
        RegisterPanel.setLocation(0, 0);

        // Center the Container
        Container.setLayout(null);
        Container.setSize(390, 150);
        Container.setLocation(35, 320);


        // Create a username label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setSize(100, 30);
        usernameLabel.setLocation(10, 10);
        Container.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setSize(200, 30);
        usernameField.setLocation(110, 10);
        Container.add(usernameField);

        // Create a password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setSize(100, 30);
        passwordLabel.setLocation(10, 50);
        Container.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setSize(200, 30);
        passwordField.setLocation(110, 50);
        Container.add(passwordField);

        // Create a register button
        JButton registerButton = new JButton("Register");
        registerButton.setSize(100, 30);
        registerButton.setLocation(10, 90);
        Container.add(registerButton);

        // when the register button is clicked, check if the username is already taken,
        // check if the password is valid, and add the user to the database
        registerButton.addActionListener(e -> {
            // Get the username and password
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Check if the password is valid
            if (!PasswordHandler.isValidPassword(password)) {
                JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one number");
                return;
            }

            // Hash the password
            String[] passwordSalt = PasswordHandler.registerUser(password);
            assert passwordSalt != null;
            String passwordHash = passwordSalt[0];
            String salt = passwordSalt[1];

            // Check if the username is already taken
            try {
                Statement statement = ConnectionHandler.connection();
                String query = "SELECT * FROM contacts_db.users WHERE username = '" + username + "'";
                if (statement.executeQuery(query).next()) {
                    JOptionPane.showMessageDialog(null, "Username is already taken");
                    return;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            // Add the user to the database
            try {
                Statement statement = ConnectionHandler.connection();
                String query = "INSERT INTO contacts_db.users (username, password_hash, salt) VALUES ('" + username + "', '" + passwordHash + "', '" + salt + "')";
                statement.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "User registered successfully");
                dispose();
                new Login();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        RegisterPanel.add(Container);
        add(RegisterPanel);
        setVisible(true);
    }
}
