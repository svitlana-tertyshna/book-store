package com.bookstore.service;

import com.bookstore.dto.BookDto;
import com.bookstore.dto.BookSearchParametersDto;
import com.bookstore.dto.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    BookDto findById(Long id);

    List<BookDto> findAll(Pageable pageable);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParametersDto bookSearchParameters);

    BookDto update(Long id, CreateBookRequestDto requestDto);
}
