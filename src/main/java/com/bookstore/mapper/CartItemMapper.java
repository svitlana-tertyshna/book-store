package com.bookstore.mapper;

import com.bookstore.config.MapperConfig;
import com.bookstore.dto.cartitem.AddCartItemRequestDto;
import com.bookstore.dto.cartitem.CartItemDto;
import com.bookstore.model.Book;
import com.bookstore.model.CartItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemDto toDto(CartItem cartItem);

    @Mapping(target = "book", ignore = true)
    CartItem toEntity(AddCartItemRequestDto requestDto);

    @AfterMapping
    default void setBooks(@MappingTarget CartItem cartItem,
                          AddCartItemRequestDto requestDto) {
        Book book = new Book();
        book.setId(requestDto.bookId());
        cartItem.setBook(book);
    }
}
