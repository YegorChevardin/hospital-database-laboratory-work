package ua.com.khpi.database.yegorchevardin.lab07.database.dao;

import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorDAO extends CreateReadUpdateDeleteDAO<Doctor> {
    List<Doctor> findByName(String name);

    Optional<Doctor> findByEmail(String email);

    List<Doctor> findByCategory(Long categoryId);
}
