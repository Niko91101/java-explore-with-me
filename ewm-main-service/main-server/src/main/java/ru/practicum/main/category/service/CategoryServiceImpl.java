package ru.practicum.main.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.category.repository.CategoryRepository;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryDto create(NewCategoryDto dto) {
        log.info("Create category: {}", dto.getName());
        if (categoryRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new ConflictException("Category name must be unique");
        }
        try {
            Category saved = categoryRepository.save(CategoryMapper.toEntity(dto));
            return CategoryMapper.toDto(saved);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Integrity constraint has been violated.", e);
        }
    }

    @Override
    @Transactional
    public void delete(long catId) {
        log.info("Delete category id={}", catId);
        if (!categoryRepository.existsById(catId)) {
            throw new NotFoundException("Category with id=" + catId + " was not found");
        }
        try {
            categoryRepository.deleteById(catId);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("The category is not empty", e);
        }
    }

    @Override
    @Transactional
    public CategoryDto update(long catId, CategoryDto dto) {
        log.info("Update category id={}, newName={}", catId, dto.getName());
        Category existing = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category with id=" + catId + " was not found"));

        if (categoryRepository.existsByNameIgnoreCase(dto.getName())
                && !existing.getName().equalsIgnoreCase(dto.getName())) {
            throw new ConflictException("Category name must be unique");
        }

        existing.setName(dto.getName());
        return CategoryMapper.toDto(categoryRepository.save(existing));
    }

    @Override
    public Page<CategoryDto> findAll(Pageable pageable) {
        log.debug("Get public categories: {}", pageable);
        return categoryRepository.findAll(pageable).map(CategoryMapper::toDto);
    }

    @Override
    public CategoryDto findById(long catId) {
        log.debug("Get public category by id={}", catId);
        return categoryRepository.findById(catId)
                .map(CategoryMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Category with id=" + catId + " was not found"));
    }
}
