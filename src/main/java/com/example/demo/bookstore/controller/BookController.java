package com.example.demo.bookstore.controller;

import com.example.demo.bookstore.model.Book;
import com.example.demo.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/book")
public class BookController {

    private final BookService bookService;
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

//    @GetMapping
//    public List<Book> getBooks(){
//        return bookService.getBooks();
//    }
//
//    @GetMapping(path="/filter")
//    public List<Book> booksInRange(@RequestParam("low") Float low, @RequestParam("high") Float high) {
//        return bookService.booksInRange(low, high);
//    }
//
//    @GetMapping(path="/{id}")
//    public Book getBook(@PathVariable Integer id)
//    {
//        return bookService.getBook(id).orElse(new Book());
//    }
//
//    @GetMapping(path="auth/{author}")
//    public List<Book> getBookByAuthor(@PathVariable String author)
//    {
//        return bookService.getBookByAuthor(author);
//    }
//
//    @PostMapping("/create")
//    public Book addBook(@RequestBody Book book)
//    {
//        return bookService.addBook(book);
//    }
//
//    @PutMapping("/stockUpdate")
//    public String updateStock(@RequestParam Integer id,
//                              @RequestParam Integer newStock)
//    {
//        bookService.updateStock(id,newStock);
//        return "Quantity Updated Successfully";
//    }
//
//    @PutMapping("/update")
//    public Book updateBook(@RequestBody Book book)
//    {
//        return bookService.updateBook(book);
//    }
//@DeleteMapping(path="/{id}")

//    public String delete(@PathVariable Integer id)
//    {
//        return bookService.deleteBook(id);
//    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books = bookService.getBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping(path="/filter")
    public ResponseEntity<List<Book>> booksInRange(@RequestParam("low") Float low, @RequestParam("high") Float high) {
        List<Book> books=bookService.getBooksInPriceRange(low, high);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Integer id)
    {
        Book book=bookService.getBook(id);
        return new ResponseEntity<>(book,HttpStatus.OK);
    }

    @GetMapping(path="auth/{author}")
    public ResponseEntity<List<Book>> getBookByAuthor(@PathVariable String author)
    {
        List<Book> books= bookService.getBookByAuthor(author);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book createdBook = bookService.addBook(book);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED); // Created status
    }

    @PutMapping("/stockUpdate")
    public ResponseEntity<String> updateStock(@RequestParam Integer id,
                                              @RequestParam Integer newStock) {
        bookService.updateStock(id, newStock);
        return new ResponseEntity<>("Quantity Updated Successfully", HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        Book updatedBook = bookService.updateBook(book);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Deletion Successful");
    }
}
