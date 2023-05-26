package ua.com.khpi.database.yegorchevardin.lab07.service.services;

/**
 * Interface for handling business logic of create read delete operations
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface CreateReadDeleteUpdateService<T> extends CreateReadDeleteService<T> {
    /**
     * Updates an entity of {@link T} datatype
     * @param item an updated entity
     */
    T update(T item);
}
