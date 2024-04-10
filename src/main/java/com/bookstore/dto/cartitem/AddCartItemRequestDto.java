package com.bookstore.dto.cartitem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddCartItemRequestDto(
        @NotNull
        Long bookId,
        @Min(1) int quantity) {
}
