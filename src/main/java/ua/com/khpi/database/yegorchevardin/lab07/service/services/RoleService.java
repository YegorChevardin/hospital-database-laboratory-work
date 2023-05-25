package ua.com.khpi.database.yegorchevardin.lab07.service.services;

import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Permission;
import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Role;

/**
 * Role service for handling business logic
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface RoleService extends CreateReadDeleteService<Role>, GetResultTableService {
    /**
     * Finds role by name
     */
    Role findByName(String name);
}
