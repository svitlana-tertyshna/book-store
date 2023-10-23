package com.bookstore.mapper;

import com.bookstore.config.MapperConfig;
import com.bookstore.dto.category.CategoryResponseDto;
import com.bookstore.dto.category.CreateCategoryDto;
import com.bookstore.model.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    Category toModel(CreateCategoryDto requestDto);

    CategoryResponseDto toDto(Category category);
}
