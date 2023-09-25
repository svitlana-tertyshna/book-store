package com.bookstore.service;

import com.bookstore.dto.category.CategoryResponseDto;
import com.bookstore.dto.category.CreateCategoryDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryResponseDto> findAll(Pageable pageable);

    CategoryResponseDto getById(Long id);

    CategoryResponseDto save(CreateCategoryDto categoryDto);

    CategoryResponseDto update(Long id, CreateCategoryDto categoryDto);

    void deleteById(Long id);
}
