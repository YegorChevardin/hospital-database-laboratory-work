package ua.com.khpi.database.yegorchevardin.lab07.database.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
}
