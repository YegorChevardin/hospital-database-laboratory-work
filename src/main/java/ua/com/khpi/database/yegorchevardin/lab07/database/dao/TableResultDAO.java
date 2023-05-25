package ua.com.khpi.database.yegorchevardin.lab07.database.dao;

import tech.tablesaw.api.Table;

/**
 * Interface for getting results in the table
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface TableResultDAO {
    /**
     * Method for getting all entities as a table result
     * @return Table object with results
     */
    Table getAllInTable();
}
