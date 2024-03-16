package com.bookstore.dto.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CategoryRequestDto {
    @NotEmpty
    private String name;
    private String description;
}
