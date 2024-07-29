package com.example.demo.bookstore.controller;

import com.example.demo.bookstore.model.Book;
import com.example.demo.bookstore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void getBooks_ShouldReturnListOfBooks() throws Exception {
        // Given
        Book book = new Book(1, "1984", "George Orwell", 200f, 30);
        List<Book> books = List.of(book);
        when(bookService.getBooks()).thenReturn(books);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/book"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'title':'1984','author':'George Orwell','price':200.0,'stockQuantity':30}]"));
    }

    @Test
    void booksInRange_ShouldReturnListOfBooks() throws Exception {
        // Given
        Book book = new Book(1, "1984", "George Orwell", 200f, 30);
        List<Book> books = List.of(book);
        when(bookService.getBooksInPriceRange(100f, 300f)).thenReturn(books);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/book/filter")
                        .param("low", "100")
                        .param("high", "300"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'title':'1984','author':'George Orwell','price':200.0,'stockQuantity':30}]"));
    }

    @Test
    void getBook_ShouldReturnBook() throws Exception {
        // Given
        Book book = new Book(1, "1984", "George Orwell", 200f, 30);
        when(bookService.getBook(1)).thenReturn(book);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id':1,'title':'1984','author':'George Orwell','price':200.0,'stockQuantity':30}"));
    }

    @Test
    void getBookByAuthor_ShouldReturnListOfBooks() throws Exception {
        // Given
        Book book = new Book(1, "1984", "George Orwell", 200f, 30);
        List<Book> books = List.of(book);
        when(bookService.getBookByAuthor("George Orwell")).thenReturn(books);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/book/auth/George Orwell"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'title':'1984','author':'George Orwell','price':200.0,'stockQuantity':30}]"));
    }

    @Test
    void addBook_ShouldReturnCreatedBook() throws Exception {
        // Given
        Book book = new Book(1, "1984", "George Orwell", 200f, 30);
        when(bookService.addBook(any(Book.class))).thenReturn(book);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/book/create")
                        .contentType("application/json")
                        .content("{\"id\":1,\"title\":\"1984\",\"author\":\"George Orwell\",\"price\":200.0,\"stockQuantity\":30}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":1,\"title\":\"1984\",\"author\":\"George Orwell\",\"price\":200.0,\"stockQuantity\":30}"));
    }

    @Test
    void updateStock_ShouldReturnSuccessMessage() throws Exception {
        // Given
        doNothing().when(bookService).updateStock(1, 50);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/book/stockUpdate")
                        .param("id", "1")
                        .param("newStock", "50"))
                .andExpect(status().isOk())
                .andExpect(content().string("Quantity Updated Successfully"));
    }

    @Test
    void updateBook_ShouldReturnUpdatedBook() throws Exception {
        // Given
        Book book = new Book(1, "1984", "George Orwell", 200f, 30);
        when(bookService.updateBook(any(Book.class))).thenReturn(book);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/book/update")
                        .contentType("application/json")
                        .content("{\"id\":1,\"title\":\"1984\",\"author\":\"George Orwell\",\"price\":200.0,\"stockQuantity\":30}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"title\":\"1984\",\"author\":\"George Orwell\",\"price\":200.0,\"stockQuantity\":30}"));
    }

    @Test
    void delete_ShouldReturnSuccessMessage() throws Exception {
        // Given
        doNothing().when(bookService).deleteBook(1);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deletion Successful"));
    }
}