package ua.com.khpi.database.yegorchevardin.lab07.database.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tech.tablesaw.api.Table;
import ua.com.khpi.database.yegorchevardin.lab07.database.constants.DatabaseConstants;
import ua.com.khpi.database.yegorchevardin.lab07.database.dao.PermissionDAO;
import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Permission;
import ua.com.khpi.database.yegorchevardin.lab07.database.exception.DatabaseException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PermissionDAOImpl implements PermissionDAO {
    private static final String INSERT_PERMISSION =
            "INSERT INTO " + DatabaseConstants.PERMISSION_TABLE.getValue() +
            "(name, description) VALUES (?, ?)";
    private static final String FIND_ALL =
            "SELECT * FROM " + DatabaseConstants.PERMISSION_TABLE.getValue();
    private static final String FIND_BY_ID =
            "SELECT * FROM " + DatabaseConstants.PERMISSION_TABLE.getValue() +
            " WHERE id = ?";
    private static final String FIND_BY_NAME =
            "SELECT * FROM " + DatabaseConstants.PERMISSION_TABLE.getValue() +
            " WHERE name = ?";
    private static final String DELETE_BY_ID =
            "DELETE FROM " + DatabaseConstants.PERMISSION_TABLE.getValue() +
            " WHERE id = ?";

    private final DataSource dataSource;

    @Override
    public Optional<Permission> getById(Long id) {
        Permission permission = null;
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)
        ) {
            int count = 0;
            statement.setLong(++count, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                permission = extractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        return Optional.ofNullable(permission);
    }

    @Override
    public List<Permission> getAll() {
        List<Permission> permissions = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        FIND_ALL
                )
        ) {
            while (resultSet.next()) {
                permissions.add(extractFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        return permissions;
    }

    @Override
    public void insert(Permission entity) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        INSERT_PERMISSION,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            int count = 0;
            statement.setString(++count, entity.getName());
            statement.setString(++count, entity.getDescription());
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
    public Table getAllInTable() {
        Table table;
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        FIND_ALL
                )
        ) {
            table = Table.read().db(resultSet, DatabaseConstants.PERMISSION_TABLE.getValue());
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        return table;
    }

    private Permission extractFromResultSet(ResultSet resultSet) throws SQLException {
        Permission permission = new Permission();
        permission.setId(resultSet.getLong("id"));
        permission.setName(resultSet.getString("name"));
        permission.setDescription(resultSet.getString("description"));
        return permission;
    }

    @Override
    public Optional<Permission> findByName(String name) {
        Permission permission = null;
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME)
        ) {
            int count = 0;
            statement.setString(++count, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                permission = extractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        return Optional.ofNullable(permission);
    }
}
