package com.example.demo.bookstore.service;

import com.example.demo.bookstore.dao.BookDao;
import com.example.demo.bookstore.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookDao dao;

    public List<Book> getBooks()
    {
        return dao.getBooks();
    }

    public Book getBook(Integer id) {
       return dao.getBook(id);
    }

    public void deleteBook(Integer id)
    {
        dao.deleteBook(id);
    }


    public List<Book> getBookByAuthor(String author) {
        return dao.getBookByAuthor(author);
    }

    public void updateStock(Integer id,Integer newStock) {
        dao.updateStock(id,newStock);
    }

    public Book addBook(Book book) {
        return dao.addBook(book);
    }

    public List<Book> getBooksInPriceRange(Float low, Float high) {
        return dao.getBooksInPriceRange(low, high);
    }

    public Book updateBook(Book book) {
        return dao.updateBook(book);
    }
}


