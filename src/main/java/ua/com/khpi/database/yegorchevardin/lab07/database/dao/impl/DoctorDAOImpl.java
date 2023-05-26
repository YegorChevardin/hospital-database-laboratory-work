package ua.com.khpi.database.yegorchevardin.lab07.database.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.com.khpi.database.yegorchevardin.lab07.database.constants.DatabaseConstants;
import ua.com.khpi.database.yegorchevardin.lab07.database.constants.DoctorTypes;
import ua.com.khpi.database.yegorchevardin.lab07.database.dao.DoctorDAO;
import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Doctor;
import ua.com.khpi.database.yegorchevardin.lab07.database.exception.DatabaseException;
import ua.com.khpi.database.yegorchevardin.lab07.service.services.CategoryService;
import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DoctorDAOImpl implements DoctorDAO {
    private static final String FIND_BY_ID =
            "SELECT * FROM " + DatabaseConstants.DOCTORS_TABLE.getValue()
                    + " WHERE id = ?";
    private static final String FIND_ALL =
            "SELECT * FROM " + DatabaseConstants.DOCTORS_TABLE.getValue();
    private static final String INSERT_DOCTOR =
            "INSERT INTO " + DatabaseConstants.DOCTORS_TABLE.getValue()
                    + "(name, second_name, email, work_experience, type, created_at, updated_at, category_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_BY_ID =
            "DELETE FROM " + DatabaseConstants.DOCTORS_TABLE.getValue()
                    + " WHERE id = ?";
    private static final String FIND_BY_NAME =
            "SELECT * FROM " + DatabaseConstants.DOCTORS_TABLE.getValue()
                    + " WHERE name = ?";
    private static final String FIND_BY_EMAIL =
            "SELECT * FROM " + DatabaseConstants.DOCTORS_TABLE.getValue()
                    + " WHERE email = ?";
    private static final String FIND_BY_CATEGORY_ID =
            "SELECT * FROM " + DatabaseConstants.DOCTORS_TABLE.getValue()
                    + " WHERE category_id = ?";
    private static final String UPDATE_DOCTORS =
            "UPDATE doctors SET name = ?, " +
                    "second_name = ?, " +
                    "email = ?, " +
                    "work_experience = ?, " +
                    "type = ? , " +
                    "created_at = ?, " +
                    "updated_at = ?, " +
                    "category_id = ? " +
                    "WHERE id = ?";
    
    private final DataSource dataSource;
    private final CategoryService categoryService;
    
    @Override
    public Optional<Doctor> getById(Long id) {
        Doctor doctor = null;
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)
        ) {
            int count = 0;
            statement.setLong(++count, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                doctor = extractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        return Optional.ofNullable(doctor);
    }

    @Override
    public List<Doctor> getAll() {
        List<Doctor> doctors = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        FIND_ALL
                )
        ) {
            while (resultSet.next()) {
                doctors.add(extractFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        return doctors;
    }

    @Override
    public void insert(Doctor entity) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        INSERT_DOCTOR,
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
    public void update(Doctor item) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection
                     .prepareStatement(UPDATE_DOCTORS)
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

    private int prepareStatement(PreparedStatement statement, Doctor doctor)
            throws SQLException {
        int count = 0;
        statement.setString(++count, doctor.getName());
        statement.setString(++count, doctor.getSecondName());
        statement.setString(++count, doctor.getEmail());
        statement.setLong(++count, doctor.getWorkExperience());
        statement.setString(++count, doctor.getType().getValue());
        statement.setTimestamp(++count, convertToTimestamp(doctor.getCreatedAt()));
        statement.setTimestamp(++count, convertToTimestamp(doctor.getUpdatedAt()));
        statement.setLong(++count, categoryService.findByName(doctor.getCategory().getName()).getId());
        return count;
    }

    private void prepareStatementForUpdate(PreparedStatement statement, Doctor doctor) throws SQLException {
        int currentRow = prepareStatement(statement, doctor);
        statement.setLong(++currentRow, doctor.getId());
    }

    private Timestamp convertToTimestamp(String localDateTimeString) {
        LocalDateTime localDateTime = LocalDateTime.parse(
                localDateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Timestamp.from(instant);
    }

    @Override
    public List<Doctor> findByName(String name) {
        List<Doctor> doctors = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME)
        ) {
            int count = 0;
            statement.setString(++count, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                doctors.add(extractFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        return doctors;
    }

    @Override
    public Optional<Doctor> findByEmail(String email) {
        Doctor doctor = null;
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL)
        ) {
            int count = 0;
            statement.setString(++count, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                doctor = extractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        return Optional.ofNullable(doctor);
    }

    @Override
    public List<Doctor> findByCategory(Long categoryId) {
        List<Doctor> doctors = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_CATEGORY_ID)
        ) {
            int count = 0;
            statement.setLong(++count, categoryId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                doctors.add(extractFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        return doctors;
    }

    private Doctor extractFromResultSet(ResultSet resultSet) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(resultSet.getLong("id"));
        doctor.setName(resultSet.getString("name"));
        doctor.setSecondName(resultSet.getString("second_name"));
        doctor.setEmail(resultSet.getString("email"));
        doctor.setWorkExperience(resultSet.getInt("work_experience"));
        try {
            doctor.setType(DoctorTypes.getTypeByName(resultSet.getString("type")));
        } catch (IllegalArgumentException e) {
            throw new DatabaseException(e.getMessage() + "Program needs to be updated");
        }
        doctor.setCreatedAt(String.valueOf(
                resultSet.getTimestamp("created_at")
                        .toLocalDateTime()
        ));
        doctor.setCreatedAt(String.valueOf(
                resultSet.getTimestamp("updated_at")
                        .toLocalDateTime()
        ));
        doctor.setCategory(categoryService.getById(resultSet.getLong("category_id")));
        return doctor;
    }
}
