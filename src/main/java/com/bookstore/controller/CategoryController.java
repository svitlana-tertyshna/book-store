package com.bookstore.controller;

import com.bookstore.dto.book.BookDtoWithoutCategoryIds;
import com.bookstore.dto.category.CategoryDto;
import com.bookstore.dto.category.CategoryRequestDto;
import com.bookstore.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/categories")
@Tag(name = "Category management", description = "Endpoints for managing categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new category (Only for admin)")
    @ApiResponse(responseCode = "201",
            description = "Category created successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CategoryDto.class))})
    public CategoryDto createCategory(@RequestBody @Valid CategoryRequestDto requestDto) {
        return categoryService.save(requestDto);
    }

    @GetMapping
    @Operation(summary = "Retrieve all categories")
    public List<CategoryDto> getAll(@ParameterObject Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a specific category by its ID")
    public CategoryDto getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update a category by its ID (only for admin)")
    @ApiResponse(responseCode = "200",
            description = "Category update successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CategoryDto.class))})
    public CategoryDto updateCategory(@PathVariable Long id,
                                      @RequestBody @Valid CategoryRequestDto requestDto) {
        return categoryService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a category by its ID (only for admin)")
    @ApiResponse(responseCode = "204",
            description = "Category deleted successfully")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
    }

    @GetMapping("/{id}/books")
    @Operation(summary = "Retrieve books by a category's ID")
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(@PathVariable Long id) {
        return categoryService.findBooksByCategoryId(id);
    }
}
