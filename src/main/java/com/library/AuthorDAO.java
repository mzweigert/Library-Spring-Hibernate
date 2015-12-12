package com.library;

import com.library.domain.Author;
import com.library.domain.Book;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by MATEUSZ on 2015-10-18.
 */
public interface AuthorDAO
{
    List<Author> getAllAuthors();
    Author getAuthorById(Author author);
    Author getAuthorByIdWithBooks(Author author);
    List<Author> getAuthorBySurname(String surname);
    List<Book> getAuthorBooks(Author author);
    Author updateAuthor(Author author);
    void deleteAuthor(Author author);
    Author addAuthor(Author author);

    SessionFactory getSessionFactory();
}
