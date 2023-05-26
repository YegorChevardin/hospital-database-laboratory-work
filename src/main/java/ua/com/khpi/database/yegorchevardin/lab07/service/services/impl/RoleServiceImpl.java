package ua.com.khpi.database.yegorchevardin.lab07.service.services.impl;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.Table;
import ua.com.khpi.database.yegorchevardin.lab07.database.dao.RoleDAO;
import ua.com.khpi.database.yegorchevardin.lab07.database.entity.Role;
import ua.com.khpi.database.yegorchevardin.lab07.service.exceptions.DataExistsException;
import ua.com.khpi.database.yegorchevardin.lab07.service.exceptions.DataNotFoundException;
import ua.com.khpi.database.yegorchevardin.lab07.service.services.RoleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final Validator validator;
    private final RoleDAO roleDAO;
    
    @Override
    public Role getById(Long id) {
        return roleDAO.getById(id).orElseThrow(
                () -> new DataNotFoundException(
                        "Role with this id not found: " + id
                )
        );
    }

    @Override
    public List<Role> getAll() {
        return roleDAO.getAll();
    }

    @Override
    public Role insert(Role entity) {
        validate(validator, entity);

        if (
                roleDAO.findByName(entity.getName()).isPresent()
        ) {
            throw new DataExistsException(
                    "Cannot insert role with this name: " + entity.getName()
                            + ", because it is already exists in database"
            );
        }
        entity.setId(null);
        roleDAO.insert(entity);
        return entity;
    }

    @Override
    public void removeById(long id) {
        if (
                roleDAO.getById(id).isEmpty()
        ) {
            throw new DataExistsException(
                    "Cannot delete role with this id, because it is already exists"
            );
        }
        roleDAO.removeById(id);
    }

    @Override
    public Table findAllAsTable() {
        return roleDAO.getAllInTable();
    }

    @Override
    public Role findByName(String name) {
        return roleDAO.findByName(name).orElseThrow(
                () -> new DataNotFoundException(
                        "Role by this name is not found: " + name
                )
        );
    }
}
