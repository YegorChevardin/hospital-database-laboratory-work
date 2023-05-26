package ua.com.khpi.database.yegorchevardin.lab07.service.services;

import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Doctor;

import java.util.List;

public interface DoctorService extends CreateReadDeleteUpdateService<Doctor> {
    List<Doctor> findByCategory(String categoryName);
    List<Doctor> findByName(String name);
    Doctor findByEmail(String email);
}
