package ua.com.khpi.database.yegorchevardin.lab07.database.dao;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Interface for preparation database after all
 * configuration objects was completed
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface DatabasePostConfig {
    /**
     * Configuration method
     */
    void config() throws SQLException, IOException;
}
