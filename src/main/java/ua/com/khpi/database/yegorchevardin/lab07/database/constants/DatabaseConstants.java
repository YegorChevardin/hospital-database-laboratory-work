package ua.com.khpi.database.yegorchevardin.lab07.database.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum constants for database values
 * @author yegorchevardin
 * @version 0.0.1
 */
@AllArgsConstructor
public enum DatabaseConstants {
    DUMP_FILE_NAME("classpath:database/dump.sql");

    @Getter
    private final String value;
}
