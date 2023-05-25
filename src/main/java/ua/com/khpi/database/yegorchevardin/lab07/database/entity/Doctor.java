package ua.com.khpi.database.yegorchevardin.lab07.database.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ua.com.khpi.database.yegorchevardin.lab07.database.constants.DoctorTypes;

import java.time.LocalDateTime;

/**
 * Class that stands for doctor data in database
 * @author yegorchevardin
 * @version 0.0.1
 */
@Data
public class Doctor {
    @Min(value = 0, message = "User id cannot be less than 0")
    private Long id;
    @NotNull(message = "User name cannot be null")
    @Length(min = 2, max = 45,
            message = "User name must be between 2 and 45 characters")
    private String name;
    @NotNull(message = "User second name cannot be null")
    @Length(min = 2, max = 50,
            message = "User second name must be between 2 and 50 characters")
    private String secondName;
    @NotNull(message = "User email cannot be null")
    @Email(message = "User email must be valid")
    private String email;
    private Integer workExperience;
    @NotNull(message = "Doctor type cannot be null")
    private DoctorTypes type;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    @NotNull(message = "Doctor category cannot be null")
    private Category category;
}
