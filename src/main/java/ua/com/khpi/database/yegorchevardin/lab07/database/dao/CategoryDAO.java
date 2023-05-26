package ua.com.khpi.database.yegorchevardin.lab07.database.dao;

import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Category;

import java.util.Optional;

/**
 * DAO for category table
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface CategoryDAO extends CreateReadDeleteDAO<Category>,
        TableResultDAO {
    /**
     * Method for finding
     * category by name
     */
    Optional<Category> findByName(String name);
}
