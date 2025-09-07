package ru.practicum.main.category.service;

import java.util.List;

import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;

public interface CategoryService {

    CategoryDto create(NewCategoryDto dto);

    void delete(Long catId);

    CategoryDto update(Long catId, CategoryDto dto);

    List<CategoryDto> getAll(int from, int size);

    CategoryDto getById(Long catId);
}
