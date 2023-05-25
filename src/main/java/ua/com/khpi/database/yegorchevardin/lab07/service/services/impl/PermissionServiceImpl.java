package ua.com.khpi.database.yegorchevardin.lab07.service.services.impl;

import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.Table;
import ua.com.khpi.database.yegorchevardin.lab07.database.dao.PermissionDAO;
import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Permission;
import ua.com.khpi.database.yegorchevardin.lab07.service.exceptions.DataExistsException;
import ua.com.khpi.database.yegorchevardin.lab07.service.exceptions.DataNotFoundException;
import ua.com.khpi.database.yegorchevardin.lab07.service.services.PermissionService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionDAO permissionDAO;
    private final Validator validator;

    @Override
    public Permission getById(Long id) {
        return permissionDAO.getById(id).orElseThrow(
                () -> new DataNotFoundException(
                        "Permission with this id is not found: " + id
                )
        );
    }

    @Override
    public List<Permission> getAll() {
        return permissionDAO.getAll();
    }

    @Override
    public Permission insert(Permission entity) {
        validate(validator, entity);

        if (
             permissionDAO.findByName(entity.getName()).isPresent()
        ) {
            throw new DataExistsException(
                    "Cannot insert permission with this name: " + entity.getName()
                    + ", because it is already exists in database"
            );
        }
        entity.setId(null);
        permissionDAO.insert(entity);
        return entity;
    }

    @Override
    public void removeById(long id) {
        if(
                permissionDAO.getById(id).isEmpty()
        ) {
            throw new DataExistsException(
                    "Cannot delete permission with this id, because it is already exists"
            );
        }
        permissionDAO.removeById(id);
    }

    @Override
    public Permission findByName(String name) {
        return permissionDAO.findByName(name).orElseThrow(
                () -> new DataNotFoundException(
                        "Permission by this name is not found: " + name
                )
        );
    }

    @Override
    public Table findAllAsTable() {
        return permissionDAO.getAllInTable();
    }
}
