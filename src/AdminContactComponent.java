import javax.swing.*;
import java.awt.*;

public class AdminContactComponent extends JPanel {
    /**
     * The name of the contact
     */
    private String contactName;
    /**
     * The number of the contact
     */
    private String contactNumber;
    /**
     * The email of the contact
     */
    private String email;
    /**
     * The address of the contact
     */
    private String address;
    /**
     * The ID of the contact
     */
    private final int ID;
    /**
     * The ID of the user that owns the contact
     */
    private int userID;
    /**
     * The container panel
     */
    private JPanel ContactContainer;
    /**
     * The name label
     */
    private final JLabel name;
    /**
     * The number label
     */
    private final JLabel number;
    /**
     * The delete button
     */
    private final JButton deleteButton;
    /**
     * The edit button
     */
    private final JButton editButton;

    /**
     * Get the delete button
     * @return the delete button
     */
    public JButton getDeleteButton() {
        return deleteButton;
    }

    /**
     * Get the edit button
     * @return the edit button
     */
    public JButton getEditButton() { return editButton; }

    /**
     * Get the ID of the contact
     * @return the ID of the contact
     */
    public int getID() {
        return ID;
    }

    /**
     * Get the ID of the user that owns the contact
     * @return the ID of the user that owns the contact
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Get the name of the contact
     * @return the name of the contact
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Get the number of the contact
     * @return the number of the contact
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * Get the email of the contact
     * @return the email of the contact
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get the address of the contact
     * @return the address of the contact
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the name of the contact
     * @param contactName the name of the contact
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
        name.setText(contactName);
    }

    /**
     * Set the number of the contact
     * @param contactNumber the number of the contact
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        number.setText(contactNumber);
    }

    /**
     * Set the email of the contact
     * @param email the email of the contact
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set the address of the contact
     * @param address the address of the contact
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Set the user ID of the contact
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Create a new contact component
     * @param ID the ID of the contact
     * @param contactName the name of the contact
     * @param contactNumber the number of the contact
     * @param email the email of the contact
     * @param address the address of the contact
     */
    public AdminContactComponent(int ID, String contactName, String contactNumber, String email, String address, int userID) {
        // Set the contact name and number
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
        this.ID = ID;
        this.userID = userID;

        // Initialize the components
        name = new JLabel(contactName);
        number = new JLabel(contactNumber);

        // Set the font for the labels
        name.setFont(new Font("Tahoma", Font.PLAIN, 24));
        number.setFont(new Font("Tahoma", Font.PLAIN, 18));

        // Add the labels to a left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(name);
        leftPanel.add(number);

        // Add a space in the middle
        JPanel middlePanel = new JPanel();

        // Add the delete and the edit buttons to the right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        deleteButton = new JButton("Delete");
        editButton = new JButton("Edit");

        // Make the buttons the same size and make the delete button #FF0000 and the edit button #0000FF
        deleteButton.setForeground(Color.decode("#FF0000"));
        deleteButton.setPreferredSize(new Dimension(100, 30));
        editButton.setPreferredSize(new Dimension(100, 30));

        rightPanel.add(editButton);
        rightPanel.add(deleteButton);

        // Add the left, middle, and right panels to the main panel
        setLayout(new BorderLayout());
        add(leftPanel, BorderLayout.WEST);
        add(middlePanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        // Style the panel
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getMinimumSize().height));

        setVisible(true);
    }
}
