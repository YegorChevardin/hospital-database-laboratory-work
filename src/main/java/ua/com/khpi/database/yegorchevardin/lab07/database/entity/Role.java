package ua.com.khpi.database.yegorchevardin.lab07.database.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * Class that stands for role data in database
 * @author yegorchevardin
 * @version 0.0.1
 */
@Data
public class Role {
    @Min(value = 0, message = "Role id value must be above the 0")
    private Long id;
    @Length(min = 3, max = 45,
            message = "Role name have to be from 3 to 45 characters")
    @NotNull(message = "Role name cannot be null")
    private String name;
}
