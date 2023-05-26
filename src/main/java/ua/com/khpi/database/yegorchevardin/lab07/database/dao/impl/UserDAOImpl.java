package ua.com.khpi.database.yegorchevardin.lab07.database.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ua.com.khpi.database.yegorchevardin.lab07.database.constants.DatabaseConstants;
import ua.com.khpi.database.yegorchevardin.lab07.database.dao.UserDAO;
import ua.com.khpi.database.yegorchevardin.lab07.database.entity.User;
import ua.com.khpi.database.yegorchevardin.lab07.database.exception.DatabaseException;
import ua.com.khpi.database.yegorchevardin.lab07.program.handlers.PasswordHashing;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {
    private static final String FIND_BY_ID =
            "SELECT * FROM " + DatabaseConstants.USERS_TABLE.getValue()
                    + " WHERE id = ?";
    private static final String FIND_ALL =
            "SELECT * FROM " + DatabaseConstants.USERS_TABLE.getValue();
    private static final String INSERT_USER =
            "INSERT INTO " + DatabaseConstants.USERS_TABLE.getValue()
                    + "(name, second_name, email, phone, password, created_at, updated_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_BY_ID =
            "DELETE FROM " + DatabaseConstants.USERS_TABLE.getValue()
                    + " WHERE id = ?";
    private static final String FIND_BY_NAME =
            "SELECT * FROM " + DatabaseConstants.USERS_TABLE.getValue()
                    + " WHERE name = ?";
    private static final String FIND_BY_EMAIL =
            "SELECT * FROM " + DatabaseConstants.USERS_TABLE.getValue()
                    + " WHERE email = ?";
    private static final String FIND_BY_PHONE =
            "SELECT * FROM " + DatabaseConstants.USERS_TABLE.getValue()
                    + " WHERE phone = ?";
    private static final String UPDATE_USERS =
            "UPDATE Users SET name = ?, " +
                    "second_name = ?, " +
                    "email = ?, " +
                    "phone = ?, " +
                    "password = ? , " +
                    "created_at = ?, " +
                    "updated_at = ? " +
                    "WHERE id = ?";
    
    private final DataSource dataSource;
    private final PasswordHashing passwordHashing;
    
    @Override
    public Optional<User> getById(Long id) {
        User user = null;
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)
        ) {
            int count = 0;
            statement.setLong(++count, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = extractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        FIND_ALL
                )
        ) {
            while (resultSet.next()) {
                users.add(extractFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        return users;
    }

    @Override
    public void insert(User entity) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        INSERT_USER,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            prepareStatement(statement, entity);
            int insertedRows = statement.executeUpdate();
            if (insertedRows > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    entity.setId(resultSet.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    @Override
    public void removeById(long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        DELETE_BY_ID
                )
        ) {
            int count = 0;
            statement.setLong(++count, id);
            int deletedRows = statement.executeUpdate();
            if (deletedRows == 0) {
                throw new DatabaseException("Cannot delete this row: " + id);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    @Override
    public void update(User item) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(UPDATE_USERS)
        ) {
            prepareStatementForUpdate(statement, item);
            int updatedCount = statement.executeUpdate();
            if (updatedCount == 0) {
                throw new SQLException(
                        "Cannot update this doctor" + item.getEmail()
                );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not update doctor with id: " + item.getId(), e);
        }
    }

    @Override
    public Optional<User> findByPhoneNumber(String phone) {
        User user = null;
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_PHONE)
        ) {
            int count = 0;
            statement.setString(++count, phone);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = extractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findByName(String name) {
        List<User> users = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME)
        ) {
            int count = 0;
            statement.setString(++count, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(extractFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        return users;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = null;
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL)
        ) {
            int count = 0;
            statement.setString(++count, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = extractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        return Optional.ofNullable(user);
    }

    private User extractFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setSecondName(resultSet.getString("second_name"));
        user.setEmail(resultSet.getString("email"));
        user.setPhone(resultSet.getString("phone"));
        user.setPassword(resultSet.getString("password"));
        user.setCreatedAt(String.valueOf(
                resultSet.getTimestamp("created_at")
                        .toLocalDateTime()
        ));
        user.setCreatedAt(String.valueOf(
                resultSet.getTimestamp("updated_at")
                        .toLocalDateTime()
        ));
        return user;
    }

    private int prepareStatement(PreparedStatement statement, User user)
            throws SQLException {
        int count = 0;
        statement.setString(++count, user.getName());
        statement.setString(++count, user.getSecondName());
        statement.setString(++count, user.getEmail());
        statement.setString(++count, user.getPhone());
        statement.setString(++count, passwordHashing.hash(user.getPassword()));
        statement.setTimestamp(++count, convertToTimestamp(user.getCreatedAt()));
        statement.setTimestamp(++count, convertToTimestamp(user.getUpdatedAt()));
        return count;
    }

    private void prepareStatementForUpdate(PreparedStatement statement, User user) throws SQLException {
        int currentRow = prepareStatement(statement, user);
        statement.setLong(++currentRow, user.getId());
    }

    private Timestamp convertToTimestamp(String localDateTimeString) {
        LocalDateTime localDateTime = LocalDateTime.parse(
                localDateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Timestamp.from(instant);
    }
}
