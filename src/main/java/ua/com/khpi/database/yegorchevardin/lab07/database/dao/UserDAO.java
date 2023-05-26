package ua.com.khpi.database.yegorchevardin.lab07.database.dao;

import ua.com.khpi.database.yegorchevardin.lab07.database.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO extends CreateReadUpdateDeleteDAO<User> {
    Optional<User> findByPhoneNumber(String phone);

    List<User> findByName(String name);

    Optional<User> findByEmail(String email);
}
