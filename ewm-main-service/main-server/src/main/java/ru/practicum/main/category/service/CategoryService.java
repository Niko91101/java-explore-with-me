package ru.practicum.main.category.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;

public interface CategoryService {
    CategoryDto create(NewCategoryDto dto);

    void delete(long catId);

    CategoryDto update(long catId, CategoryDto dto);

    Page<CategoryDto> findAll(Pageable pageable);

    CategoryDto findById(long catId);
}
