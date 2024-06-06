import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Admin extends JFrame {
    /**
     * The text field for searching contacts
     */
    private JTextField textField1;
    /**
     * The scroll pane for the contacts
     */
    private JScrollPane ContactsPanel;
    /**
     * The add button
     */
    private JButton Add;
    /**
     * The main panel
     */
    private JPanel MainPanel;

    /**
     * Create a new MainFrame with a main panel, a text field, a scroll pane, and an add button
     * The main panel contains the text field, scroll pane, and add button
     * The text field is used to search contacts
     * The scroll pane contains the contacts
     * The add button creates a new contact
     */
    public Admin() {
        setTitle("Admin Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(390, 844);
        setResizable(false);

        int padding = 5;

        // Stylize the text field with some padding and rounded corners
        textField1.setFont(new Font("Tahoma", Font.PLAIN, 24));
        textField1.setBorder(BorderFactory.createEmptyBorder(2*padding, 2*padding, 2*padding, 2*padding));
        textField1.setBorder(BorderFactory.createCompoundBorder(textField1.getBorder(), BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 1)));
        textField1.setBorder(BorderFactory.createCompoundBorder(textField1.getBorder(), BorderFactory.createEmptyBorder(2*padding, 2*padding, 2*padding, 2*padding)));
        textField1.setBackground(new Color(0, 0, 0, 0));

        // Stylize the scroll pane
        ContactsPanel.setBorder(BorderFactory.createEmptyBorder(0, padding, padding, padding));
        ContactsPanel.setBorder(BorderFactory.createCompoundBorder(ContactsPanel.getBorder(), BorderFactory.createEmptyBorder(0, padding, padding, padding)));
        ContactsPanel.getVerticalScrollBar().setUnitIncrement(16);
        ContactsPanel.setBackground(new Color(0, 0, 0, 0));

        // Stylize the add button
        Add.setFont(new Font("Tahoma", Font.PLAIN, 24));
        Add.setBorder(BorderFactory.createEmptyBorder(2*padding, 2*padding, 2*padding, 2*padding));
        Add.setBorder(BorderFactory.createCompoundBorder(Add.getBorder(), BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 1)));
        Add.setBorder(BorderFactory.createCompoundBorder(Add.getBorder(), BorderFactory.createEmptyBorder(2*padding, 2*padding, 2*padding, 2*padding)));
        Add.setBackground(new Color(0, 0, 0, 0));

        // Add a listener to the add button
        Add.addActionListener(e -> {
            // Show a dialog with 2 text fields for the name and number
            JTextField nameField = new JTextField();
            JTextField numberField = new JTextField();
            JTextField emailField = new JTextField();
            JTextField addressField = new JTextField();
            JTextField userIDField = new JTextField();
            Object[] message = {
                    "Name:", nameField,
                    "Number:", numberField,
                    "Email:", emailField,
                    "Address:", addressField,
                    "User ID:", userIDField
            };
            int option = JOptionPane.showConfirmDialog(this, message, "Add Contact", JOptionPane.OK_CANCEL_OPTION);
            // if a name and number were entered, add the contact
            if (option == JOptionPane.OK_OPTION) {
                String name = nameField.getText();
                String number = numberField.getText();
                String email = emailField.getText();
                String address = addressField.getText();
                int userIDInt = Integer.parseInt(userIDField.getText());
                if (!name.trim().isEmpty() && !number.trim().isEmpty() && !userIDField.getText().trim().isEmpty()) {
                    addContact(name, number, email, address, userIDInt);
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a name and number");
                }
            }
        });

        textField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                recreateContactsPanel(ContactsPanel);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                recreateContactsPanel(ContactsPanel);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                recreateContactsPanel(ContactsPanel);
            }
        });

        // Create the contacts panel
        recreateContactsPanel(ContactsPanel);

        setContentPane(MainPanel);
        setVisible(true);
    }

    /**
     * Add a contact to the database and panel
     * @param name the name of the contact
     * @param number the number of the contact
     */
    void addContact(String name, String number, String email, String address, int userID) {
        // Add the contact to the database
        Statement statement = ConnectionHandler.connection();
        try {
            String query = "INSERT INTO contacts_db.contacts (user_id, name, phone_number, email, address) VALUES (" + userID + ", '" + name + "', '" + number + "', '" + email + "', '" + address + "')";
            statement.executeUpdate(query);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        // Add the contact to the panel reread the contacts from the database
        recreateContactsPanel(ContactsPanel);
    }

    /**
     * Update the contacts from the database
     * @return the contacts
     */
    ArrayList<ArrayList<String>> updateContacts() {
        ArrayList<ArrayList<String>> contacts = new ArrayList<>();
        String text = textField1.getText();
        try {
            Statement statement = ConnectionHandler.connection();
            String query = "SELECT * FROM contacts_db.contacts WHERE name LIKE '%" + text + "%' OR phone_number LIKE '%" + text + "%' OR email LIKE '%" + text + "%' OR address LIKE '%" + text + "%'";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                ArrayList<String> contact = new ArrayList<>();
                contact.add(resultSet.getString("id"));
                contact.add(resultSet.getString("name"));
                contact.add(resultSet.getString("phone_number"));
                contact.add(resultSet.getString("email"));
                contact.add(resultSet.getString("address"));
                contact.add(resultSet.getString("user_id"));
                contacts.add(contact);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contacts;
    }

    /**
     * Recreate the contacts panel
     * @param ContactsPanel the scroll pane for the contacts
     */
    void recreateContactsPanel(JScrollPane ContactsPanel) {
        // Create the contacts panel
        JPanel contactsPanel = new JPanel();
        contactsPanel.setLayout(new BoxLayout(contactsPanel, BoxLayout.Y_AXIS));

        // Get the contacts from the database
        ArrayList<ArrayList<String>> contacts = updateContacts();

        // Add each contact to the panel
        for (ArrayList<String> contact : contacts) {
            AdminContactComponent contactComponent = new AdminContactComponent(Integer.parseInt(contact.get(0)), contact.get(1), contact.get(2), contact.get(3), contact.get(4), Integer.parseInt(contact.get(5)));
            contactComponent.getDeleteButton().addActionListener(e -> {
                // Delete the contact from the database and remove it from the panel with a confirmation dialog
                int option = JOptionPane.showConfirmDialog(this, "Are you sure\nyou want to delete\nthis contact?", "Delete Contact", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    try {
                        Statement statement = ConnectionHandler.connection();
                        statement.executeUpdate("DELETE FROM contacts_db.contacts WHERE id=" + contactComponent.getID());
                    } catch (SQLException exception) {
                        throw new RuntimeException(exception);
                    }
                    contactsPanel.remove(contactComponent);
                    contactsPanel.revalidate();
                    contactsPanel.repaint();
                }
            });
            // if the contact is clicked, show a dialog with the name and number
            contactComponent.getEditButton().addActionListener(e -> {
                JTextField nameField = new JTextField(contactComponent.getContactName());
                JTextField numberField = new JTextField(contactComponent.getContactNumber());
                JTextField emailField = new JTextField(contactComponent.getEmail());
                JTextField addressField = new JTextField(contactComponent.getAddress());
                JTextField userIDField = new JTextField(String.valueOf(contactComponent.getUserID()));
                Object[] message = {
                        "Name:", nameField,
                        "Number:", numberField,
                        "Email:", emailField,
                        "Address:", addressField,
                        "User ID:", userIDField
                };
                int option = JOptionPane.showConfirmDialog(this, message, "Edit Contact", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String name = nameField.getText();
                    String number = numberField.getText();
                    String email = emailField.getText();
                    String address = addressField.getText();
                    int userIDInt = Integer.parseInt(userIDField.getText());
                    if (!name.trim().isEmpty() && !number.trim().isEmpty() && !userIDField.getText().trim().isEmpty()) {
                        try {
                            Statement statement = ConnectionHandler.connection();
                            statement.executeUpdate("UPDATE contacts_db.contacts SET name='" + name + "', phone_number='" + number + "', email='" + email + "', address='" + address + "', user_id=" + userIDInt + " WHERE id=" + contactComponent.getID());
                        } catch (SQLException exception) {
                            throw new RuntimeException(exception);
                        }
                        contactComponent.setContactName(name);
                        contactComponent.setContactNumber(number);
                        contactComponent.setEmail(email);
                        contactComponent.setAddress(address);
                        contactComponent.setUserID(userIDInt);
                    } else {
                        JOptionPane.showMessageDialog(this, "Please enter a name, number and user ID");
                    }
                }
            });
            contactsPanel.add(contactComponent);
            contactsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some spacing
        }

        // Add the panel to the scroll pane
        ContactsPanel.setViewportView(contactsPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Admin::new);
    }
}
