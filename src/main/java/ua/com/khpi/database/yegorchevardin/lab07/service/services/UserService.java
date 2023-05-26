package ua.com.khpi.database.yegorchevardin.lab07.service.services;

import ua.com.khpi.database.yegorchevardin.lab07.database.entity.User;

import java.util.List;

public interface UserService extends CreateReadDeleteUpdateService<User> {
    User findByPhoneNumber(String phone);

    List<User> findByName(String name);

    User findByEmail(String email);
}
