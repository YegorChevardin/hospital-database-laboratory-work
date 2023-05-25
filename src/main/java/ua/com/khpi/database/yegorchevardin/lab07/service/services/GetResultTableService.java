package ua.com.khpi.database.yegorchevardin.lab07.service.services;

import tech.tablesaw.api.Table;

/**
 * Interface for defining method for getting results as a table
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface GetResultTableService {
    /**
     * Method for getting results as a table
     */
    Table findAllAsTable();
}
