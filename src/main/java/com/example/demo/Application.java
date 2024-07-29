package com.example.demo;

import com.example.demo.bookstore.model.Book;
import com.example.demo.bookstore.repository.BookRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
//		SpringApplication.run(Application.class, args);
//		ConfigurableApplicationContext context=SpringApplication.run(Application.class,args);
//		Book b1=context.getBean(Book.class);
//		Book b2=context.getBean(Book.class);
//		BookRepo repo=context.getBean(BookRepo.class);
//
//		b1=new Book(1,"Harry Potter","JK Rowling",250f,100);
//
//		b2=new Book(2,"Percy Jackson","Rick Riordan",175f,50);

		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		// Get the BookRepo bean from the context
		BookRepo repo = context.getBean(BookRepo.class);

		// Create and save a new Book
		Book b1 = new Book(1, "Harry Potter", "JK Rowling", 250f, 100);
		Book b2 = new Book(2, "Percy Jackson", "Rick Riordan", 175f, 50);

//		repo.save(b1);
//		repo.save(b2);
		System.out.println(repo.findAll());

	}

}
