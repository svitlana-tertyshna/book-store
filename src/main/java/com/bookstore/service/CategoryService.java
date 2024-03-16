package com.bookstore.service;

import com.bookstore.dto.book.BookDtoWithoutCategoryIds;
import com.bookstore.dto.category.CategoryDto;
import com.bookstore.dto.category.CategoryRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto save(CategoryRequestDto requestDto);

    CategoryDto update(Long id, CategoryRequestDto requestDto);

    void deleteById(Long id);

    List<BookDtoWithoutCategoryIds> findBooksByCategoryId(Long id);
}
