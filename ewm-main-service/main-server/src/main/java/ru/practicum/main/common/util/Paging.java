package ru.practicum.main.common.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public final class Paging {
    private Paging() {
    }

    public static Pageable of(int from, int size) {
        int page = from / size;
        return PageRequest.of(page, size);
    }
}
