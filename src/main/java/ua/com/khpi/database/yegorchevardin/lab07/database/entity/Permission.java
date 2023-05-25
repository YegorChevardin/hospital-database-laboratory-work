package ua.com.khpi.database.yegorchevardin.lab07.database.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * Class that stands for permission data in database
 * @author yegorchevardin
 * @version 0.0.1
 */
@Data
public class Permission {
    @Min(value = 0, message = "Permission id value must be above the 0")
    private Long id;
    @Length(min = 3, max = 45,
            message = "Permission name have to be from 3 to 45 characters")
    @NotNull(message = "Permission name cannot be null")
    private String name;
    @Length(min = 5, max = 250,
            message = "Permission description have to be from 5 to 250 characters")
    private String description;
}
