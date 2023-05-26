package ua.com.khpi.database.yegorchevardin.lab07.database.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tech.tablesaw.api.Table;
import ua.com.khpi.database.yegorchevardin.lab07.database.constants.DatabaseConstants;
import ua.com.khpi.database.yegorchevardin.lab07.database.dao.CategoryDAO;
import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Category;
import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Role;
import ua.com.khpi.database.yegorchevardin.lab07.database.exception.DatabaseException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryDAOImpl implements CategoryDAO {
    private static final String FIND_BY_ID =
            "SELECT * FROM " + DatabaseConstants.CATEGORIES_TABLE.getValue()
                    + " WHERE id = ?";
    private static final String FIND_ALL =
            "SELECT * FROM " + DatabaseConstants.CATEGORIES_TABLE.getValue();
    private static final String INSERT_CATEGORY =
            "INSERT INTO " + DatabaseConstants.CATEGORIES_TABLE.getValue() + "(name) VALUES (?)";
    private static final String DELETE_BY_ID =
            "DELETE FROM " + DatabaseConstants.CATEGORIES_TABLE.getValue()
                    + " WHERE id = ?";
    private static final String FIND_BY_NAME =
            "SELECT * FROM " + DatabaseConstants.CATEGORIES_TABLE.getValue()
                    + " WHERE name = ?";
    
    private final DataSource dataSource;
    
    @Override
    public Optional<Category> findByName(String name) {
        Category category = null;
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME)
        ) {
            int count = 0;
            statement.setString(++count, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                category = extractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        return Optional.ofNullable(category);
    }

    @Override
    public Optional<Category> getById(Long id) {
        Category category = null;
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)
        ) {
            int count = 0;
            statement.setLong(++count, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                category = extractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        return Optional.ofNullable(category);
    }

    @Override
    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        FIND_ALL
                )
        ) {
            while (resultSet.next()) {
                categories.add(extractFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        return categories;
    }

    @Override
    public void insert(Category entity) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        INSERT_CATEGORY,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            int count = 0;
            statement.setString(++count, entity.getName());
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
            table = Table.read().db(resultSet, DatabaseConstants.ROLES_TABLE.getValue());
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        return table;
    }

    private Category extractFromResultSet(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getLong("id"));
        category.setName(resultSet.getString("name"));
        return category;
    }
}
