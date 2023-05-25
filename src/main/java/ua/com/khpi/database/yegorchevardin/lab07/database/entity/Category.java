package ua.com.khpi.database.yegorchevardin.lab07.database.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * Category class that stands for entity object from the information
 * from database
 * @author yegorchevardin
 * @version 0.0.1
 */
@Data
public class Category {
    @Min(value = 0, message = "Category id value must be above the 0")
    private Long id;
    @Length(min = 3, max = 45,
            message = "Category name have to be from 3 to 45 characters")
    @NotNull(message = "Category name cannot be null")
    private String name;
}
