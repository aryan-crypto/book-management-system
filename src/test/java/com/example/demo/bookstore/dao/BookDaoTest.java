package com.example.demo.bookstore.dao;

import com.example.demo.bookstore.exceptionhandling.IdNotFoundException;
import com.example.demo.bookstore.model.Book;
import com.example.demo.bookstore.repository.BookRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BookDaoTest {

    @Mock
    private BookRepo repo;

    @InjectMocks
    private BookDao underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addBook_ShouldSaveAndReturnBook() {
        // Given
        Book book = new Book(1, "1984", "George Orwell", 200f, 30);
        when(repo.save(book)).thenReturn(book);

        // When
        Book result = underTest.addBook(book);

        // Then
        verify(repo).save(book); // Verify that repo.save() was called with the correct book
        assertEquals(book, result, "The returned book should be the same as the one passed in");
    }

    @Test
    void getAllBooks()
    {
        underTest.getBooks();
        verify(repo).findAll();
    }

    @Test
    void getBooksByAuthor_ShouldReturnBooks() {

        Book book = new Book(3, "The Hobbit", "J.R.R. Tolkien", 300f, 50);
        List<Book> books = Collections.singletonList(book);
        when(repo.findByAuthor("J.R.R. Tolkien")).thenReturn(books);

        List<Book> result = underTest.getBookByAuthor("J.R.R. Tolkien");

        assertEquals(1, result.size(), "The size of the list should be 1");
        assertEquals("The Hobbit", result.get(0).getTitle(), "The title of the book should be 'The Hobbit'");
        assertEquals("J.R.R. Tolkien", result.get(0).getAuthor(), "The author of the book should be 'J.R.R. Tolkien'");
    }

    @Test
    void getBooksByAuthor_WhenNoBooksFound() {
        // Arrange
        when(repo.findByAuthor("Unknown Author")).thenReturn(Collections.emptyList());

        // Act
        List<Book> result = underTest.getBookByAuthor("Unknown Author");

        // Assert
        assertEquals(0, result.size(), "The size of the list should be 0 when no books are found for the author");
    }

    @Test
    void testGetBookByID(){
        Integer bookId = 3;
        Book expectedBook = new Book(bookId, "The Hobbit", "J.R.R. Tolkien", 300f, 50);
        when(repo.findById(bookId)).thenReturn(java.util.Optional.of(expectedBook));

        // When
        Optional<Book> actualBook = repo.findById(bookId);

        // Then
        assertEquals(expectedBook, actualBook.orElse(null));
    }


    @Test
    void getBooksInPriceRange_ShouldReturnBooksWithinPriceRange() {
        // Given
        Float low = 100f;
        Float high = 300f;
        Book book1 = new Book(1, "The Hobbit", "J.R.R. Tolkien", 300f, 50);
        List<Book> booksInRange = List.of(book1);
        when(repo.findByPriceBetween(low, high)).thenReturn(booksInRange);

        // When
        List<Book> result = underTest.getBooksInPriceRange(low, high);

        // Then
        verify(repo).findByPriceBetween(low,high);
    //    assertEquals(1, result.size());
    //    assertEquals(book1, result.get(0) );
    }

    @Test
    void getBooksInPriceRange_WhenNoBooksFound() {
        // Given
        Float low = 100f;
        Float high = 300f;
        when(repo.findByPriceBetween(low, high)).thenReturn(Collections.emptyList());

        // When
        List<Book> result = underTest.getBooksInPriceRange(low, high);

        // Then
        assertEquals(0, result.size());
    }

    @Test
        void deleteBook_WhenIdExists() {
        // Given
        Integer id = 1;
        when(repo.existsById(id)).thenReturn(true);

        // When
        underTest.deleteBook(id);

        // Then
        verify(repo).deleteById(id); // Verify that deleteById was called
    }

    @Test
    void deleteBook_WhenIdDoesNotExist() {
        // Given
        Integer id = 1;
        //when
        when(repo.existsById(id)).thenReturn(false);

        // Then
        assertThrows(IdNotFoundException.class, () -> underTest.deleteBook(id),
                "Expected deleteBook to throw IdNotFoundException when ID does not exist");
    }
    @Test
    void updateStock_WhenBookExists() {
        // Given
        Integer id = 1;
        Integer newStock = 50;
        Book existingBook = new Book(id, "Book Title", "Author", 200f, 30);
        when(repo.findById(id)).thenReturn(Optional.of(existingBook));

        // When
        underTest.updateStock(id, newStock);

        // Then
        verify(repo).findById(id); // Verify that findById was called
        assertEquals(newStock, existingBook.getStockQuantity(), "The stock quantity should be updated");
        verify(repo).save(existingBook); // Verify that save was called with the updated book
    }

    @Test
    void updateStock_WhenBookDoesNotExist() {
        // Given
        Integer id = 1;
        Integer newStock = 50;
        when(repo.findById(id)).thenReturn(Optional.empty());

        // When
        underTest.updateStock(id, newStock);

        // Then
        verify(repo).findById(id); // Verify that findById was called
        verify(repo, never()).save(any(Book.class)); // Verify that save was never called
    }
    @Test
    void updateBook_WhenIdExists() {
        // Given
        Book book = new Book(1, "Updated Book Title", "Updated Author", 250f, 40);
        when(repo.existsById(book.getId())).thenReturn(true);

        Book result = underTest.updateBook(book);

        verify(repo).existsById(book.getId()); // Verify that existsById was called
        verify(repo).save(book); // Verify that save was called with the updated book
        assertEquals(book, result, "The returned book should be the same as the one passed in");
    }

    @Test
    void updateBook_WhenIdDoesNotExist() {
        // Given
        Book book = new Book(1, "Updated Book Title", "Updated Author", 250f, 40);
        when(repo.existsById(book.getId())).thenReturn(false);

        // When & Then
        IdNotFoundException thrown = assertThrows(IdNotFoundException.class, () -> underTest.updateBook(book),
                "Expected updateBook to throw IdNotFoundException when ID does not exist");
        assertEquals("Cannot Update as Id does not Exist", thrown.getMessage(), "Exception message should match");
    }
}
