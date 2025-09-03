package ru.practicum.main.category.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.model.Category;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CategoryMapper {

    public static Category toEntity(NewCategoryDto dto) {
        return Category.builder()
                .name(dto.getName())
                .build();
    }

    public static Category toEntity(Long id, CategoryDto dto) {
        return Category.builder()
                .id(id)
                .name(dto.getName())
                .build();
    }

    public static CategoryDto toDto(Category entity) {
        return CategoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
