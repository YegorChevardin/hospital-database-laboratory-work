package ua.com.khpi.database.yegorchevardin.lab07.database.dao;

import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Category;

import java.util.Optional;

/**
 * DAO for category table
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface CategoryDAO extends CreateReadDeleteDAO<Optional<Category>>,
        TableResultDAO {
}
