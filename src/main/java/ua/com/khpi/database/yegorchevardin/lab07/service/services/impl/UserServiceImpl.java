package ua.com.khpi.database.yegorchevardin.lab07.service.services.impl;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.khpi.database.yegorchevardin.lab07.database.dao.UserDAO;
import ua.com.khpi.database.yegorchevardin.lab07.database.entity.User;
import ua.com.khpi.database.yegorchevardin.lab07.service.exceptions.DataExistsException;
import ua.com.khpi.database.yegorchevardin.lab07.service.exceptions.DataNotFoundException;
import ua.com.khpi.database.yegorchevardin.lab07.service.services.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final Validator validator;

    @Override
    public User getById(Long id) {
        return findById(id);
    }

    private User findById(Long id) {
        return userDAO.getById(id).orElseThrow(
                () -> new DataNotFoundException(
                        "User with this id did not found:" + id
                )
        );
    }

    @Override
    public List<User> getAll() {
        return userDAO.getAll();
    }

    @Override
    public User insert(User item) {
        validate(validator, item);
        if (userDAO.findByEmail(item.getEmail()).isPresent()
                && !userDAO.findByEmail(item.getEmail()).get().getId().equals(item.getId())) {
            throw new DataExistsException(
                    "Cannot use this email, because user with this email already exists: "
                            + item.getEmail()
            );
        }
        if (userDAO.findByPhoneNumber(item.getPhone()).isPresent()
                && !userDAO.findByPhoneNumber(item.getPhone()).get().getId().equals(item.getId())) {
            throw new DataExistsException(
                    "Cannot use this phone number, because user with this phone already exists: "
                            + item.getPhone()
            );
        }
        userDAO.insert(item);
        return findById(item.getId());
    }

    @Override
    public void removeById(long id) {
        findById(id);
        userDAO.removeById(id);
    }

    @Override
    public User update(User item) {
        validate(validator, item);
        if (item.getId() == null
                || userDAO.getById(item.getId()).isEmpty()
        ) {
            throw new DataNotFoundException(
                    "Cannot update user that does not exists"
            );
        }
        if (userDAO.findByEmail(item.getEmail()).isPresent()
                && !userDAO.findByEmail(item.getEmail()).get().getId().equals(item.getId())) {
            throw new DataExistsException(
                    "Cannot update this user to new email, because user with this email already exists: "
                            + item.getEmail()
            );
        }
        if (userDAO.findByPhoneNumber(item.getPhone()).isPresent()
                && !userDAO.findByPhoneNumber(item.getPhone()).get().getId().equals(item.getId())) {
            throw new DataExistsException(
                    "Cannot update this user to new phone number, because user with this phone already exists: "
                            + item.getPhone()
            );
        }
        item.setUpdatedAt(String.valueOf(LocalDateTime.now()));
        userDAO.update(item);
        return findById(item.getId());
    }

    @Override
    public User findByPhoneNumber(String phone) {
        return userDAO.findByPhoneNumber(phone).orElseThrow(
                () -> new DataNotFoundException(
                        "User with this phone number does not exists"
                )
        );
    }

    @Override
    public List<User> findByName(String name) {
        return userDAO.findByName(name);
    }

    @Override
    public User findByEmail(String email) {
        return userDAO.findByEmail(email).orElseThrow(
                () -> new DataNotFoundException(
                        "User with this email does not exists"
                )
        );
    }
}
