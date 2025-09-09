package ru.practicum.main.compilation.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewCompilationDto {
    private List<Long> events;

    @Builder.Default
    private Boolean pinned = Boolean.FALSE;

    @NotBlank(message = "must not be blank")
    @Size(min = 1, max = 50, message = "size must be between 1 and 50")
    private String title;
}
