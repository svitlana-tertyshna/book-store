package com.bookstore.repository.book;

import com.bookstore.dto.book.BookSearchParametersDto;
import com.bookstore.model.Book;
import com.bookstore.repository.SpecificationBuilder;
import com.bookstore.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private static final String AUTHOR = "author";

    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto bookSearchParameters) {
        Specification<Book> specification = Specification.where(null);
        if (bookSearchParameters.author() != null && bookSearchParameters.author().length > 0) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider(AUTHOR).getSpecification(bookSearchParameters.author()));
        }
        return specification;
    }
}
