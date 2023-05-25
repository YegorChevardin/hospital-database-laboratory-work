package ua.com.khpi.database.yegorchevardin.lab07.database.dao;

import java.util.List;
import java.util.Optional;

/**
 * Interface that represents create, read and delete operations in the database
 * @param <T> type of entity for processing
 * @author Yehor Chevardin
 * @version 1.0.0
 */
public interface CreateReadDeleteDAO<T> {
    /**
     * Method for getting element by id from the database
     * @param id id of the element
     * @return Object of {@link T} datatype
     */
    Optional<T> getById(Long id);

    /**
     * Method for getting a collection of objects {@link T} datatype
     * @return A collection of {@link T} objects
     */
    List<T> getAll();

    /**
     * Method for saving an entity of {@link T} datatype
     * @param entity an {@link T} entity to save
     */
    void insert(T entity);

    /**
     * Removes an entity of {@link T} datatype by its id
     * @param id an ID of {@link T} entity
     */
    void removeById(long id);
}
