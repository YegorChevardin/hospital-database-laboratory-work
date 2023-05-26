package ua.com.khpi.database.yegorchevardin.lab07.database.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * Class that stands for user data in database
 * @author yegorchevardin
 * @version 0.0.1
 */
@Data
public class User {
    private static final String PHONE_PATTERN = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s" +
            "\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$";
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
    @NotNull(message = "User phone number cannot be null")
    @Pattern(regexp = PHONE_PATTERN,
            message = "User phone number must be valid")
    private String phone;
    @NotNull(message = "User password must be not null")
    @Length(min = 5, max = 128, message = "User password must be between 5 and 128 characters")
    private String password;
    private String createdAt = String.valueOf(LocalDateTime.now());
    private String updatedAt = String.valueOf(LocalDateTime.now());
}
