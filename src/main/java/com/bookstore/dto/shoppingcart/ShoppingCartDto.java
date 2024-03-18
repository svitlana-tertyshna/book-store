package com.bookstore.dto.shoppingcart;

import com.bookstore.dto.cartitem.CartItemDto;
import java.util.List;

public record ShoppingCartDto(
        Long id,
        Long userId,
        List<CartItemDto> cartItems) {
}
