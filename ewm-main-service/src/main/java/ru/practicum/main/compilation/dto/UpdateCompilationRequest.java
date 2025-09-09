package ru.practicum.main.compilation.dto;

import java.util.List;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCompilationRequest {

    private List<Long> events;

    private Boolean pinned;

    // null — поле не меняем; если пришло, то 1..50
    @Size(min = 1, max = 50, message = "size must be between 1 and 50")
    private String title;
}
