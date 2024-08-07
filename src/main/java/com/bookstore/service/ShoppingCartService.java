package com.bookstore.service;

import com.bookstore.dto.cartitem.AddCartItemRequestDto;
import com.bookstore.dto.cartitem.CartItemDto;
import com.bookstore.dto.cartitem.UpdateQuantityCartItemDto;
import com.bookstore.dto.shoppingcart.ShoppingCartDto;
import com.bookstore.model.User;

public interface ShoppingCartService {
    CartItemDto addBookToShoppingCart(User user, AddCartItemRequestDto requestDto);

    ShoppingCartDto getUserShoppingCart(User user);

    CartItemDto updateCartItemQuantity(Long cartItemId,
                                       UpdateQuantityCartItemDto requestDto,
                                       User user);

    void deleteBookByCartItemId(User user, Long cartItemId);

    void cleanShoppingCart(Long userId);
}
