package com.example.demo.bookstore.repository;


import com.example.demo.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book,Integer> {

    @Query("SELECT b FROM Book b WHERE b.author = :author")
    List<Book> findByAuthor(String author);

    List<Book> findByPriceBetween(Float minPrice, Float maxPrice);

}
