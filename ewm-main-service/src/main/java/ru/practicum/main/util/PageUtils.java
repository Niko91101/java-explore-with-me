package ru.practicum.main.util;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class PageUtils {

    private PageUtils() {
    }

    public static Pageable offset(int from, int size) {
        return new OffsetBasedPageRequest(from, size);
    }

    public static Pageable offset(int from, int size, Sort sort) {
        return new OffsetBasedPageRequest(from, size, sort);
    }

    public static Sort sortBy(String property, boolean asc) {
        return asc ? Sort.by(property).ascending() : Sort.by(property).descending();
    }

    public static Sort sortBy(List<String> props, boolean asc) {
        if (props == null || props.isEmpty()) {
            return Sort.unsorted();
        }
        Sort base = Sort.by(props.get(0));
        for (int i = 1; i < props.size(); i++) {
            base = base.and(Sort.by(props.get(i)));
        }
        return asc ? base.ascending() : base.descending();
    }

    public static Pageable page(int page, int size) {
        return PageRequest.of(page, size);
    }
}
