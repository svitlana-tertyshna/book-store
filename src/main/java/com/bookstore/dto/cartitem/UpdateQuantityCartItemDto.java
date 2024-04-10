package com.bookstore.dto.cartitem;

import jakarta.validation.constraints.Min;

public record UpdateQuantityCartItemDto(
        @Min(1) int quantity) {
}
