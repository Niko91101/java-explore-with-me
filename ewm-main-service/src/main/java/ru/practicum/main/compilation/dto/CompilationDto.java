package ru.practicum.main.compilation.dto;

import java.util.List;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.main.event.dto.EventShortDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompilationDto {

    private Long id;

    @Size(min = 1, max = 50, message = "size must be between 1 and 50")
    private String title;

    private Boolean pinned;

    private List<EventShortDto> events;
}
