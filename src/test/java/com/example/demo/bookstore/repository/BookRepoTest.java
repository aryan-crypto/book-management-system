package com.example.demo.bookstore.repository;

import com.example.demo.bookstore.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest
//class BookRepoTest {
//
//    @Autowired
//    private BookRepo underTest;
//
//    @Test
//    void checkIfBookExists() {
//        Integer id = 6;
//        Book book = new Book(id, "Matilda", "Roald Dahl", 250f, 100);
//
//        underTest.save(book);
//
//        boolean exists = underTest.existsById(id);
//
////        assertTrue(exists);
//        assertThat(exists).isTrue();
//    }
////}
//
//    @Test
//    void findByAuthor() {
//        //given
//        Book bookTest=underTest.findById(6).orElse(new Book());
//        //when
//        boolean exists=underTest.findByAuthor("Roald Dahl");
//        //then
//    }
//
//    @Test
//    void findByPriceBetween() {
//    }
//}
@DataJpaTest
class BookRepoTest {

    @Autowired
    private BookRepo underTest;

    @BeforeEach
    void setUp() {
        underTest.save(new Book(1, "Matilda", "Roald Dahl", 250f, 100));
        underTest.save(new Book(2, "Charlie and the Chocolate Factory", "Roald Dahl", 150f, 200));
        underTest.save(new Book(3, "The Hobbit", "J.R.R. Tolkien", 300f, 50));
        underTest.save(new Book(4, "Harry Potter and the Sorcerer's Stone", "J.K. Rowling", 200f, 150));
    }

    @Test
    void checkIfBookExists() {
        Integer id = 1;
        boolean exists = underTest.existsById(id);
        assertTrue(exists);
    }

    @Test
    void checkIfBookDoesNotExist(){
        Integer nonExistentId = 999;
        boolean doesNotExist = underTest.existsById(nonExistentId);
        assertThat(doesNotExist).isFalse();
    }

    @Test
    void findByAuthor() {
        // Given
        String author = "Roald Dahl";

        // When
        List<Book> booksByAuthor = underTest.findByAuthor(author);

        // Then
        assertThat(booksByAuthor).hasSize(2)
                .extracting(Book::getTitle)
                .containsExactlyInAnyOrder("Matilda", "Charlie and the Chocolate Factory");
    }

    @Test
    void findByPriceBetween() {
        // Given
        float minPrice = 100f;
        float maxPrice = 250f;

        // When
        List<Book> booksInRange = underTest.findByPriceBetween(minPrice, maxPrice);

        // Then
        assertThat(booksInRange).hasSize(3)
                .extracting(Book::getTitle)
                .containsExactlyInAnyOrder("Matilda", "Charlie and the Chocolate Factory", "Harry Potter and the Sorcerer's Stone");
    }
}