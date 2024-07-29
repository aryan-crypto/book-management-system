package com.example.demo.bookstore.service;

import com.example.demo.bookstore.exceptionhandling.IdNotFoundException;
import com.example.demo.bookstore.repository.BookRepo;
import com.example.demo.bookstore.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepo repo;

    public List<Book> getBooks()
    {
        return repo.findAll();
    }

    public Book getBook(Integer id) {
        Optional<Book> book=repo.findById(id);
        if(book.isPresent()){
            return book.get();
        }
        else {
            throw new IdNotFoundException("Id does not exist");
        }
    }

    public void deleteBook(Integer id)
    {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new IdNotFoundException("Id does not exist");
        }
    }


    public List<Book> getBookByAuthor(String author) {
        return repo.findByAuthor(author);
    }

    public void updateStock(Integer id,Integer newStock) {
        Optional<Book> b=repo.findById(id);
        if(b.isPresent())
        {
            Book currentBook=b.get();
            currentBook.setStockQuantity(newStock);
            repo.save(currentBook);
        }
    }

    public Book addBook(Book book) {
        repo.save(book);
        return book;
    }

    public List<Book> getBooksInPriceRange(Float low, Float high) {
        return repo.findByPriceBetween(low, high);
    }

    public Book updateBook(Book book) {
        Integer id= book.getId();
        if(repo.existsById(id)) {
            repo.save(book);
            return book;
        }
        else {
            throw new IdNotFoundException("Cannot Update as Id does not Exist");
        }
    }

}


