import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionHandler {
    /**
     * The url of the database
     * The database is called contacts_db
     * The tables are called users and contacts
     * The columns of the users table are id, username, password_hash, and salt
     * The columns of the contacts table are id, user_id, name, number, email, and address
     */
    static String url = "jdbc:mysql://localhost:3306";
    /**
     * The username of the database
     * The default password is root
     */
    static String username = "root";
    /**
     * The password of the database
     * The default password is Fartooth60
     */
    static String password = "Fartooth60";

    /**
     * Connect to the database
     * Create the database if it does not exist
     * Create the tables if they do not exist
     * Use the database
     * @return the statement
     */
    public static Statement connection() {
        Statement statement;
        try {
            statement = DriverManager.getConnection(url, username, password).createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS contacts_db");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS contacts_db.users (id INT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), password_hash VARCHAR(255), salt VARCHAR(255))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS contacts_db.contacts (id INT PRIMARY KEY AUTO_INCREMENT, user_id INT, name VARCHAR(255), number VARCHAR(255), email VARCHAR(255), address VARCHAR(255))");
            statement.executeUpdate("USE contacts_db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return statement;
    }
}
