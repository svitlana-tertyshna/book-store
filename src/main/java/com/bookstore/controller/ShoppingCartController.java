package com.bookstore.controller;

import com.bookstore.dto.cartitem.AddCartItemRequestDto;
import com.bookstore.dto.cartitem.CartItemDto;
import com.bookstore.dto.cartitem.UpdateQuantityCartItemDto;
import com.bookstore.dto.shoppingcart.ShoppingCartDto;
import com.bookstore.model.User;
import com.bookstore.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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
@RequestMapping("/cart")
@RequiredArgsConstructor
@Tag(name = "Shopping cart management", description = "Endpoints for managing shopping cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @Operation(summary = "Get all info about the cart", description = "Get all info about the cart")
    @ApiResponse(responseCode = "200", description = "Cart information",
            content = {@Content(mediaType = "application/json")})
    public ShoppingCartDto getShoppingCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.getUserShoppingCart(user);
    }

    @PostMapping
    @Operation(summary = "Add cart item to the cart",
            description = "Add needed book and its quantity")
    public CartItemDto addBookToCart(@RequestBody @Valid AddCartItemRequestDto requestDto,
                                     Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addBookToShoppingCart(user, requestDto);
    }

    @PutMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Update a cart item in a given cart",
            description = "Change the number of a book")
    @ApiResponse(responseCode = "200", description = "Updated",
            content = {@Content(mediaType = "application/json")})
    public CartItemDto updateQuantityInCart(
            @PathVariable Long cartItemId,
            @RequestBody @Valid UpdateQuantityCartItemDto requestDto,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.updateCartItemQuantity(cartItemId, requestDto, user);
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Delete a cart item from a given cart",
            description = "Delete a cart item from a given cart")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartItem(@PathVariable Long cartItemId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        shoppingCartService.deleteBookByCartItemId(user, cartItemId);
    }
}
