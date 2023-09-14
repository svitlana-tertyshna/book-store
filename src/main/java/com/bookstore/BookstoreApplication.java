package com.bookstore;

import com.bookstore.model.Book;
import com.bookstore.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookstoreApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book kobzar = new Book();
            kobzar.setTitle("Kobzar");
            kobzar.setAuthor("Taras Shevchenko");
            kobzar.setIsbn("isbn");
            kobzar.setPrice(BigDecimal.valueOf(14.99));
            kobzar.setDescription("");

            Book dune = new Book();
            dune.setTitle("Dune");
            dune.setAuthor("Franklin Patrick Herbert");
            dune.setIsbn("isbn1");
            dune.setPrice(BigDecimal.valueOf(39.99));

            bookService.save(kobzar);
            bookService.save(dune);
            System.out.println(bookService.findAll());
        };
    }
}
