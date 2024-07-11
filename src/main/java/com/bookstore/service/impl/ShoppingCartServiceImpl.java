package com.bookstore.service.impl;

import com.bookstore.dto.cartitem.AddCartItemRequestDto;
import com.bookstore.dto.cartitem.CartItemDto;
import com.bookstore.dto.cartitem.UpdateQuantityCartItemDto;
import com.bookstore.dto.shoppingcart.ShoppingCartDto;
import com.bookstore.exception.EntityNotFoundException;
import com.bookstore.exception.ShoppingCartUpdateException;
import com.bookstore.mapper.CartItemMapper;
import com.bookstore.mapper.ShoppingCartMapper;
import com.bookstore.model.Book;
import com.bookstore.model.CartItem;
import com.bookstore.model.ShoppingCart;
import com.bookstore.model.User;
import com.bookstore.repository.book.BookRepository;
import com.bookstore.repository.cartitem.CartItemRepository;
import com.bookstore.repository.shoppingcart.ShoppingCartRepository;
import com.bookstore.service.ShoppingCartService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final BookRepository bookRepository;

    @Override
    public CartItemDto addBookToShoppingCart(User user, AddCartItemRequestDto requestDto) {
        ShoppingCart shoppingCart = getShoppingCartByUser(user);
        Book book = bookRepository.findById(requestDto.bookId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can`t find book by id = " + requestDto.bookId()));
        duplicateCheck(shoppingCart, requestDto.bookId());

        CartItem cartItem = cartItemMapper.toEntity(requestDto);
        cartItem.setBook(book);
        cartItem.setShoppingCart(shoppingCart);
        cartItemRepository.save(cartItem);

        List<CartItem> cartItems = shoppingCart.getCartItems();
        cartItems.add(cartItem);
        shoppingCart.setCartItems(cartItems);
        shoppingCartRepository.save(shoppingCart);

        return cartItemMapper.toDto(cartItem);
    }

    @Override
    public ShoppingCartDto getUserShoppingCart(User user) {
        return shoppingCartMapper.toDto(getShoppingCartByUser(user));
    }

    @Override
    public CartItemDto updateCartItemQuantity(Long cartItemId,
                                              UpdateQuantityCartItemDto requestDto,
                                              User user) {
        CartItem cartItemForUpdate = cartItemRepository.findById(cartItemId).orElseThrow(
                () -> new EntityNotFoundException("Can't find cart item by id = " + cartItemId));
        ShoppingCart shoppingCart = getShoppingCartByUser(user);
        isUserHaveCartItem(shoppingCart, cartItemForUpdate);

        cartItemForUpdate.setQuantity(requestDto.quantity());
        return cartItemMapper.toDto(cartItemRepository.save(cartItemForUpdate));
    }

    @Override
    public void deleteBookByCartItemId(User user, Long cartItemId) {
        CartItem cartItemForDelete = cartItemRepository.findById(cartItemId).orElseThrow(
                () -> new EntityNotFoundException("Can't find cart item by id = " + cartItemId));
        ShoppingCart shoppingCart = getShoppingCartByUser(user);
        isUserHaveCartItem(shoppingCart, cartItemForDelete);

        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public void cleanShoppingCart(Long userId) {
        cartItemRepository.deleteAllByShoppingCartId(userId);
    }

    private ShoppingCart getShoppingCartByUser(User user) {
        return shoppingCartRepository.findShoppingCartByUser(user)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can`t find shopping cart for user with id = " + user.getId()));
    }

    private void duplicateCheck(ShoppingCart shoppingCart,
                                Long bookId) {
        List<CartItem> cartItems = shoppingCart.getCartItems();
        for (CartItem cartItem : cartItems) {
            if (cartItem.getBook().getId().equals(bookId)) {
                throw new ShoppingCartUpdateException("You already have this book in your cart "
                        + cartItem.getBook().getTitle());
            }
        }
    }

    private void isUserHaveCartItem(ShoppingCart shoppingCart, CartItem cartItem) {
        if (!cartItem.getShoppingCart().getId().equals(shoppingCart.getId())) {
            throw new ShoppingCartUpdateException(
                    "This user don't have in shopping cart cart item with id = "
                    + cartItem.getId());
        }
    }
}
