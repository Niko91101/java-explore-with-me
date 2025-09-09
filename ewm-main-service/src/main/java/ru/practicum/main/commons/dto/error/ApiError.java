package ru.practicum.main.commons.dto.error;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiError {

    private String status;

    private String reason;

    private String message;

    private String timestamp;

    private List<String> errors;
}
