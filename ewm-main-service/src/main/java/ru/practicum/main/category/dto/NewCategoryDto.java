package ru.practicum.main.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewCategoryDto {

    @NotBlank(message = "must not be blank")
    @Size(min = 1, max = 50, message = "size must be between 1 and 50")
    private String name;
}
