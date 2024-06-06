import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.Statement;

public class Login extends JFrame {
    /**
     * The login panel
     */
    private JPanel LoginPanel;
    /**
     * The container panel
     */
    private JPanel Container;

    /**
     * Create a new Login frame with a login panel and a container panel
     * The login panel contains the container panel
     * The container panel contains the username and password fields and the login and register buttons
     * The login button checks if the username and password are correct
     * The register button creates a new Register frame
     */
    public Login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(390, 844);
        setResizable(false);
        setTitle("Login");


        // Create a login panel with fields for username and password, and a login button and center it
        LoginPanel.setLayout(null);
        LoginPanel.setSize(390, 844);
        LoginPanel.setLocation(0, 0);

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

        // Create a login button
        JButton loginButton = new JButton("Login");
        loginButton.setSize(100, 30);
        loginButton.setLocation(10, 90);
        Container.add(loginButton);

        // when the login button is clicked, check if the username and password are correct
        loginButton.addActionListener(e -> {
            // Get the username and password
            String username = usernameField.getText();
            String enteredPassword = new String(passwordField.getPassword());
            String storedHashedPassword;
            String storedSalt;

            Statement statement = ConnectionHandler.connection();

            // Check if the username and password are correct
            try {
                if (username.equals("admin") && enteredPassword.equals("admin")) {
                    SwingUtilities.invokeLater(Admin::new);
                    dispose();
                    return;
                }

                // Check if the username exists
                ResultSet resultSetUsername = statement.executeQuery("SELECT username FROM contacts_db.users WHERE username='" + username + "'");
                if (!resultSetUsername.next()) {
                    JOptionPane.showMessageDialog(this, "Incorrect username");
                    return;
                }

                // Get the stored salt and hashed password
                ResultSet resultSetSaltPassword = statement.executeQuery("SELECT salt, password_hash FROM contacts_db.users WHERE username='" + username + "'");
                resultSetSaltPassword.next();
                storedSalt = resultSetSaltPassword.getString("salt");
                storedHashedPassword = resultSetSaltPassword.getString("password_hash");

                // Authenticate the user
                boolean isAuthenticated = PasswordHandler.authenticateUser(enteredPassword, storedSalt, storedHashedPassword);
                if (isAuthenticated) {
                    // If the user is authenticated, get the user's ID and create a new MainFrame
                    ResultSet resultSetID = statement.executeQuery("SELECT id FROM contacts_db.users WHERE username='" + username + "'");
                    resultSetID.next();
                    int userID = resultSetID.getInt("id");
                    System.out.println("User ID: " + userID);
                    SwingUtilities.invokeLater(() -> new MainFrame(userID));
                    dispose();
                } else {
                    // If the user is not authenticated, show an error message
                    JOptionPane.showMessageDialog(this, "Incorrect username or password");
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        // Create a register button
        JButton registerButton = new JButton("Register");
        registerButton.setSize(100, 30);
        registerButton.setLocation(110, 90);
        Container.add(registerButton);

        // When the register button is clicked, create a new RegisterFrame
        registerButton.addActionListener(e -> {
                    SwingUtilities.invokeLater(Register::new);
                    dispose();
            }
        );

        LoginPanel.add(Container);
        add(LoginPanel);
        setVisible(true);
    }
}
