package ua.com.khpi.database.yegorchevardin.lab07.service.services.impl;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.Table;
import ua.com.khpi.database.yegorchevardin.lab07.database.dao.CategoryDAO;
import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Category;
import ua.com.khpi.database.yegorchevardin.lab07.service.exceptions.DataExistsException;
import ua.com.khpi.database.yegorchevardin.lab07.service.exceptions.DataNotFoundException;
import ua.com.khpi.database.yegorchevardin.lab07.service.services.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDAO categoryDAO;
    private final Validator validator;

    @Override
    public Category findByName(String name) {
        return categoryDAO.findByName(name).orElseThrow(
                () -> new DataNotFoundException(
                        "Category by this name is not found: " + name
                )
        );
    }

    @Override
    public Category getById(Long id) {
        return categoryDAO.getById(id).orElseThrow(
                () -> new DataNotFoundException(
                        "Category with this id not found: " + id
                )
        );
    }

    @Override
    public List<Category> getAll() {
        return categoryDAO.getAll();
    }

    @Override
    public Category insert(Category entity) {
        validate(validator, entity);

        if (
                categoryDAO.findByName(entity.getName()).isPresent()
        ) {
            throw new DataExistsException(
                    "Cannot insert category with this name: " + entity.getName()
                            + ", because it is already exists in database"
            );
        }
        entity.setId(null);
        categoryDAO.insert(entity);
        return entity;
    }

    @Override
    public void removeById(long id) {
        if (
                categoryDAO.getById(id).isEmpty()
        ) {
            throw new DataExistsException(
                    "Cannot delete category with this id, because it is already exists"
            );
        }
        categoryDAO.removeById(id);
    }

    @Override
    public Table findAllAsTable() {
        return categoryDAO.getAllInTable();
    }
}
