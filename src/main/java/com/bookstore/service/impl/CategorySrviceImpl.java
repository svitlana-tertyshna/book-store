package com.bookstore.service.impl;

import com.bookstore.dto.book.BookDtoWithoutCategoryIds;
import com.bookstore.dto.category.CategoryDto;
import com.bookstore.dto.category.CategoryRequestDto;
import com.bookstore.exception.EntityNotFoundException;
import com.bookstore.mapper.BookMapper;
import com.bookstore.mapper.CategoryMapper;
import com.bookstore.model.Category;
import com.bookstore.repository.book.BookRepository;
import com.bookstore.repository.category.CategoryRepository;
import com.bookstore.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategorySrviceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category by id = " + id));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto save(CategoryRequestDto requestDto) {
        Category category = categoryMapper.toEntity(requestDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto update(Long id, CategoryRequestDto requestDto) {
        categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category by id = " + id));
        Category category = categoryMapper.toEntity(requestDto);
        category.setId(id);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category by id = " + id));
        categoryRepository.deleteById(id);
    }

    @Override
    public List<BookDtoWithoutCategoryIds> findBooksByCategoryId(Long id) {
        return bookRepository.findAllByCategoriesId(id).stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }
}
