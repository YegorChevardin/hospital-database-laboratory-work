package ua.com.khpi.database.yegorchevardin.lab07.service.services;

/**
 * Interface for defining actions on application startup
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface ProgramStartupService {
    /**
     * Method for executing dump for creating
     * or refreshing the database
     */
    void refreshDatabase();
}
