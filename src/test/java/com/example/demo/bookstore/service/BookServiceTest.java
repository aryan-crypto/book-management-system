package com.example.demo.bookstore.service;

import com.example.demo.bookstore.dao.BookDao;
import com.example.demo.bookstore.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookDao dao;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBooks_ShouldReturnListOfBooks() {
        // Given
        Book book = new Book(1, "1984", "George Orwell", 200f, 30);
        List<Book> books = List.of(book);
        when(dao.getBooks()).thenReturn(books);

        // When
        List<Book> result = bookService.getBooks();

        // Then
        verify(dao).getBooks();
        assertEquals(books, result);
    }

    @Test
    void getBook_ShouldReturnBook() {
        // Given
        Integer id = 1;
        Book book = new Book(id, "1984", "George Orwell", 200f, 30);
        when(dao.getBook(id)).thenReturn(book);

        // When
        Book result = bookService.getBook(id);

        // Then
        verify(dao).getBook(id);
        assertEquals(book, result);
    }

    @Test
    void deleteBook_ShouldCallDaoMethod() {
        // Given
        Integer id = 1;

        // When
        bookService.deleteBook(id);

        // Then
        verify(dao).deleteBook(id);
    }

    @Test
    void getBookByAuthor_ShouldReturnListOfBooks() {
        // Given
        String author = "George Orwell";
        Book book = new Book(1, "1984", author, 200f, 30);
        List<Book> books = List.of(book);
        when(dao.getBookByAuthor(author)).thenReturn(books);

        // When
        List<Book> result = bookService.getBookByAuthor(author);

        // Then
        verify(dao).getBookByAuthor(author);
        assertEquals(books, result);
    }

    @Test
    void updateStock_ShouldCallDaoMethod() {
        // Given
        Integer id = 1;
        Integer newStock = 50;

        // When
        bookService.updateStock(id, newStock);

        // Then
        verify(dao).updateStock(id, newStock);
    }

    @Test
    void addBook_ShouldReturnAddedBook() {
        // Given
        Book book = new Book(1, "1984", "George Orwell", 200f, 30);
        when(dao.addBook(book)).thenReturn(book);

        // When
        Book result = bookService.addBook(book);

        // Then
        verify(dao).addBook(book);
        assertEquals(book, result);
    }

    @Test
    void getBooksInPriceRange_ShouldReturnListOfBooks() {
        // Given
        Float low = 100f;
        Float high = 300f;
        Book book = new Book(1, "1984", "George Orwell", 200f, 30);
        List<Book> books = List.of(book);
        when(dao.getBooksInPriceRange(low, high)).thenReturn(books);

        // When
        List<Book> result = bookService.getBooksInPriceRange(low, high);

        // Then
        verify(dao).getBooksInPriceRange(low, high);
        assertEquals(books, result);
    }

    @Test
    void updateBook_ShouldReturnUpdatedBook() {
        // Given
        Book book = new Book(1, "1984", "George Orwell", 200f, 30);
        when(dao.updateBook(book)).thenReturn(book);

        // When
        Book result = bookService.updateBook(book);

        // Then
        verify(dao).updateBook(book);
        assertEquals(book, result);
    }
}
