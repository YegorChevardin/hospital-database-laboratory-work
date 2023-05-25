package ua.com.khpi.database.yegorchevardin.lab07.database.dao;

import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Role;

import java.util.Optional;

/**
 * DAO for role table
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface RoleDAO extends CreateReadDeleteDAO<Optional<Role>>, TableResultDAO {
}
