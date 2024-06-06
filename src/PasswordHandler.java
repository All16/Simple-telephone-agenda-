import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHandler {
    /**
     * Register a user
     * @param password the password of the user
     * @return an array containing the salt and hashed password
     */
    public static String[] registerUser(String password) {
        try {
            // Generate a random salt
            byte[] salt = generateSalt();

            // Hash the password with the salt
            String hashedPassword = hashPassword(password, salt);

            // Convert salt and hashed password to Base64 for storage
            String encodedSalt = Base64.getEncoder().encodeToString(salt);
            String encodedHashedPassword = Base64.getEncoder().encodeToString(hashedPassword.getBytes());

            // Return salt and hashed password for future authentication
            return new String[]{encodedHashedPassword, encodedSalt};
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Authenticate a user
     * @param enteredPassword the password entered by the user
     * @param storedSalt the salt stored in the database
     * @param storedHashedPassword the hashed password stored in the database
     * @return true if the user is authenticated, false otherwise
     */
    public static boolean authenticateUser(String enteredPassword, String storedSalt, String storedHashedPassword) {
        try {
            // Decode stored salt and hashed password from Base64
            byte[] salt = Base64.getDecoder().decode(storedSalt);
            String hashedPassword = new String(Base64.getDecoder().decode(storedHashedPassword));

            // Hash the entered password with the stored salt
            String hashedEnteredPassword = hashPassword(enteredPassword, salt);

            // Compare the hashed passwords
            return hashedEnteredPassword.equals(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Generate a random salt
     * @return the salt
     */
    private static byte[] generateSalt() {
        // Create a secure random number generator
        SecureRandom secureRandom = new SecureRandom();

        // Specify the length of the salt (16 bytes for SHA-256)
        byte[] salt = new byte[16];

        // Generate a random salt
        secureRandom.nextBytes(salt);

        return salt;
    }

    /**
     * Hash a password with a salt
     * @param password the password to hash
     * @param salt the salt to use
     * @return the hashed password
     * @throws NoSuchAlgorithmException if the algorithm is not found
     */
    private static String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        // Concatenate password and salt
        byte[] combined = concatenateByteArrays(password.getBytes(), salt);

        // Use SHA-256 for hashing
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(combined);

        // Convert hashedBytes to a Base64-encoded string
        return Base64.getEncoder().encodeToString(hashedBytes);
    }

    /**
     * Concatenate two byte arrays
     * @param array1 the first array
     * @param array2 the second array
     * @return the concatenated array
     */
    private static byte[] concatenateByteArrays(byte[] array1, byte[] array2) {
        byte[] result = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    /**
     * Check if the password contains at least one uppercase letter, one lowercase letter, and one number
     * @param password the password to check
     * @return true if the password is valid, false otherwise
     */
    public static boolean isValidPassword(String password) {
        // Check if the password is at least 8 characters long
        if (password.length() < 8) {
            return false;
        }

        // Check if the password contains at least one uppercase letter, one lowercase letter, and one number
        boolean containsUppercase = false;
        boolean containsLowercase = false;
        boolean containsNumber = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                containsUppercase = true;
            } else if (Character.isLowerCase(c)) {
                containsLowercase = true;
            } else if (Character.isDigit(c)) {
                containsNumber = true;
            }
        }
        return containsUppercase && containsLowercase && containsNumber;
    }
}
