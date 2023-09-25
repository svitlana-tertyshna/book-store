package com.bookstore.service;

import com.bookstore.dto.category.CategoryResponseDto;
import com.bookstore.dto.category.CreateCategoryDto;
import com.bookstore.exception.EntityNotFoundException;
import com.bookstore.mapper.BookMapper;
import com.bookstore.mapper.CategoryMapper;
import com.bookstore.model.Category;
import com.bookstore.repository.category.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final BookMapper bookMapper;

    @Override
    public List<CategoryResponseDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryResponseDto getById(Long id) {
        return categoryMapper.toDto(categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find category by id: " + id)));
    }

    @Override
    public CategoryResponseDto save(CreateCategoryDto categoryDto) {
        return categoryMapper.toDto(categoryRepository
                .save(categoryMapper.toModel(categoryDto)));
    }

    @Override
    public CategoryResponseDto update(Long id, CreateCategoryDto categoryDto) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Can't find and update category by id: " + id);
        }
        Category category = categoryMapper.toModel(categoryDto);
        category.setId(id);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Can't find and delete category by id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
