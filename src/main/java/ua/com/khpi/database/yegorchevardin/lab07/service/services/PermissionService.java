package ua.com.khpi.database.yegorchevardin.lab07.service.services;

import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Permission;

/**
 * Permission service for handling business logic
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface PermissionService extends CreateReadDeleteService<Permission>, GetResultTableService {
    /**
     * Finds permission by name
     */
    Permission findByName(String name);
}
