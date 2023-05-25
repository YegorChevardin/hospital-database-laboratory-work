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
    DUMP_FILE_NAME("classpath:database/dump.sql"),
    INSERTION_DUMP_FILE_NAME("classpath:database/insertions.sql"),
    PERMISSION_TABLE("permissions");

    @Getter
    private final String value;
}
