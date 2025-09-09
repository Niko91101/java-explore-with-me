package ru.practicum.main.commons.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class LocationEmbeddable {

    private Double lat;

    private Double lon;
}
