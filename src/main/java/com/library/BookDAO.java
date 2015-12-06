package com.library;

import com.library.domain.Author;
import com.library.domain.Book;
import com.library.domain.Reader;
import org.hibernate.SessionFactory;

import java.util.List;

public interface BookDAO
{

    List<Book> getAllBooks();
    Book getBookById(Book book);
    List<Book> getBookByTitle(String title);
    List<Author> getBookAuthors(Book book);
    List<Reader> getBookReaders(Book book);
    Book updateBook(Book book);
    void deleteBook(Book book);
    Book addBook(Book book);

    SessionFactory getSessionFactory();
}
