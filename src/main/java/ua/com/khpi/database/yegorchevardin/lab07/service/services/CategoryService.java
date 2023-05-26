package ua.com.khpi.database.yegorchevardin.lab07.service.services;

import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Category;

/**
 * Service for category business logic
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface CategoryService extends CreateReadDeleteService<Category>, GetResultTableService {
    /**
     * Method for finding category by name
     */
    Category findByName(String name);
}
