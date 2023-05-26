package ua.com.khpi.database.yegorchevardin.lab07.program.handlers;

/**
 * Interface for defining methods to hash the password
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface PasswordHashing {
    /**
     * Method for hashing password
     */
    String hash(String password);

    /**
     * Method for authenticating password
     */
    boolean authenticate(String password, String token);
}
