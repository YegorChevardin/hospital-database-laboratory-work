package ua.com.khpi.database.yegorchevardin.lab07.service.services;

import java.util.List;
import java.util.Optional;

/**
 * Interface for handling business logic of create read delete operations
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface CreateReadDeleteService<T> extends ValidationService<T> {
    /**
     * Method for getting element by id from the database
     * @param id id of the element
     * @return Object of {@link T} datatype
     */
    T getById(Long id);

    /**
     * Method for getting a collection of objects {@link T} datatype
     * @return A collection of {@link T} objects
     */
    List<T> getAll();

    /**
     * Method for saving an entity of {@link T} datatype
     * @param entity an {@link T} entity to save
     */
    T insert(T entity);

    /**
     * Removes an entity of {@link T} datatype by its id
     * @param id an ID of {@link T} entity
     */
    void removeById(long id);
}
