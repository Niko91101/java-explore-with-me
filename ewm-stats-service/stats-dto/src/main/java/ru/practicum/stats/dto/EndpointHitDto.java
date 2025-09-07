package ru.practicum.stats.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;


import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EndpointHitDto {
    @NotBlank
    private String app;


    @NotBlank
    private String uri;


    @NotBlank
    @Pattern(regexp = "^\\d+\\.\\d+\\.\\d+\\.\\d+$", message = "ip must be IPv4")
    private String ip;


    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}