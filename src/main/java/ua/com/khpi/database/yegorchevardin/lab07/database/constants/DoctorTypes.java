package ua.com.khpi.database.yegorchevardin.lab07.database.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ua.com.khpi.database.yegorchevardin.lab07.database.exception.DatabaseException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Doctor type constants
 * @author yegorchevardin
 * @version 0.0.1
 */
@AllArgsConstructor
public enum DoctorTypes {
    DOCTOR("doctor"),
    NURSE("nurse");

    @Getter
    private final String value;

    /**
     * Method for returning DoctorTypes enum by its string value
     */
    public static DoctorTypes getTypeByName(String typeName) {
        for (DoctorTypes currentValue : values()) {
            if (typeName.equals(currentValue.getValue())) {
                return currentValue;
            }
        }
        throw new IllegalArgumentException(
                "Cannot find type with this name: " + typeName
        );
    }
}
