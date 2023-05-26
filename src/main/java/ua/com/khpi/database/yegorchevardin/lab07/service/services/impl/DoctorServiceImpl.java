package ua.com.khpi.database.yegorchevardin.lab07.service.services.impl;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.khpi.database.yegorchevardin.lab07.database.dao.DoctorDAO;
import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Doctor;
import ua.com.khpi.database.yegorchevardin.lab07.service.exceptions.DataExistsException;
import ua.com.khpi.database.yegorchevardin.lab07.service.exceptions.DataNotFoundException;
import ua.com.khpi.database.yegorchevardin.lab07.service.services.CategoryService;
import ua.com.khpi.database.yegorchevardin.lab07.service.services.DoctorService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorDAO doctorDAO;
    private final CategoryService categoryService;
    private final Validator validator;

    @Override
    public Doctor getById(Long id) {
        return findById(id);
    }

    private Doctor findById(Long id) {
        return doctorDAO.getById(id).orElseThrow(
                () -> new DataNotFoundException(
                        "Cannot find doctor with this id: " + id
                )
        );
    }

    @Override
    public List<Doctor> getAll() {
        return doctorDAO.getAll();
    }

    @Override
    public Doctor insert(Doctor entity) {
        validate(validator, entity);
        categoryService.findByName(entity.getCategory().getName());
        doctorDAO.insert(entity);
        return entity;
    }

    @Override
    public void removeById(long id) {
        findById(id);
        doctorDAO.removeById(id);
    }

    @Override
    public Doctor update(Doctor item) {
        validate(validator, item);
        if (item.getId() == null
                || doctorDAO.getById(item.getId()).isEmpty()
        ) {
            throw new DataNotFoundException(
                    "Cannot update doctor that does not exists"
            );
        }
        if (doctorDAO.findByEmail(item.getEmail()).isPresent()
                && !doctorDAO.findByEmail(item.getEmail()).get().getId().equals(item.getId())) {
            throw new DataExistsException(
                    "Cannot update this doctor to new email, because doctor with this email already exists: "
                            + item.getEmail()
            );
        }
        categoryService.findByName(item.getCategory().getName());
        item.setUpdatedAt(String.valueOf(LocalDateTime.now()));
        doctorDAO.update(item);
        return findById(item.getId());
    }

    @Override
    public List<Doctor> findByCategory(String categoryName) {
        return doctorDAO.findByCategory(categoryService.findByName(categoryName).getId());
    }

    @Override
    public List<Doctor> findByName(String name) {
        return doctorDAO.findByName(name);
    }

    @Override
    public Doctor findByEmail(String email) {
        return doctorDAO.findByEmail(email).orElseThrow(
                () -> new DataNotFoundException(
                        "Doctor with this email does not exists: " + email
                )
        );
    }
}
