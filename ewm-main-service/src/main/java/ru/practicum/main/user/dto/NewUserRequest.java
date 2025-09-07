package ru.practicum.main.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewUserRequest {

    @NotBlank(message = "must not be blank")
    @Size(min = 2, max = 250, message = "size must be between 2 and 250")
    private String name;

    @NotBlank(message = "must not be blank")
    @Email(message = "must be a well-formed email address")
    @Size(min = 6, max = 254, message = "size must be between 6 and 254")
    private String email;
}
