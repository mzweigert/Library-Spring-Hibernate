package com.library;

import com.library.domain.Author;
import com.library.domain.Book;
import com.library.domain.Reader;
import org.hibernate.SessionFactory;

import java.util.List;

public interface BookDAO
{
    SessionFactory getSessionFactory();
    List<Book> getAllBooks();
    Book getBookById(Book book);
    Book getBookByIdWithAuthors(Book book);
    Book getBookByIdWithHirings(Book book);
    List<Book> getBookByTitle(String title);
    Book updateBook(Book book);
    void deleteBook(Book book);
    Book addBook(Book book);


}
