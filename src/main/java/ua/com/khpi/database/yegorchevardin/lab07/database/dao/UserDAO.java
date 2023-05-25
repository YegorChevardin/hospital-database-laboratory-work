package ua.com.khpi.database.yegorchevardin.lab07.database.dao;

import ua.com.khpi.database.yegorchevardin.lab07.database.entity.User;

import java.util.Optional;

public interface UserDAO extends CreateReadUpdateDeleteDAO<Optional<User>> {
}
