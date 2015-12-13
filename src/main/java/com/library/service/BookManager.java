package com.library.service;



import com.library.BookDAO;
import com.library.config.HibernateConfig;
import com.library.domain.Author;
import com.library.domain.Book;
import com.library.domain.Reader;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@ComponentScan(basePackageClasses = HibernateConfig.class)
public class BookManager implements BookDAO
{

    @Autowired
    SessionFactory sessionFactory;

    public SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }


    public List<Book> getAllBooks()
    {
        return sessionFactory.getCurrentSession().getNamedQuery("book.all").list();
    }

    public List<Author> getBookAuthors(Book book)
    {
        Book bookWithAuthors = sessionFactory.getCurrentSession().get(Book.class, book.getIdBook());
        Hibernate.initialize(bookWithAuthors.getAuthors());

        return bookWithAuthors.getAuthors();
    }

    public List<Reader> getBookReaders(Book book)
    {
        return null;
    }

    public Book getBookById(Book book)
    {
        return sessionFactory.getCurrentSession().get(Book.class, book.getIdBook());
    }

    public Book getBookByIdWithAuthors(Book book)
    {
        Book bookWithAuthors = sessionFactory.getCurrentSession().get(Book.class, book.getIdBook());
        Hibernate.initialize(bookWithAuthors.getAuthors());

        return bookWithAuthors;
    }

    public List<Book> getBookByTitle(String title)
    {
        return sessionFactory.getCurrentSession().getNamedQuery("book.byTitle").setString("title", title).list();

    }


    public Book updateBook(Book book)
    {
        return (Book) sessionFactory.getCurrentSession().merge(book);
    }


    public void deleteBook(Book book)
    {
        sessionFactory.getCurrentSession().delete(book);
    }

    public Book addBook(Book book)
    {
        long idBook = ((Long)sessionFactory.getCurrentSession().save(book)).longValue();
        book.setIdBook(idBook);
        return book;
    }

   /* public void clearBooks()
    {
        try
        {
            deleteAllBooksStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }*/

}
