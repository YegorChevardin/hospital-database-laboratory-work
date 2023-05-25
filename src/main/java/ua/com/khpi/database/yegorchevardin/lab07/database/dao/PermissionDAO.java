package ua.com.khpi.database.yegorchevardin.lab07.database.dao;

import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Permission;

import java.util.Optional;

/**
 * DAO for permissions table
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface PermissionDAO extends CreateReadDeleteDAO<Permission>,
        TableResultDAO {
    /**
     * Finds permission by name
     */
    Optional<Permission> findByName(String name);
}
