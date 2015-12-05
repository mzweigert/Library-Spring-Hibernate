package com.library.service;


import com.library.AuthorDAO;
import com.library.config.HibernateConfig;
import com.library.domain.Author;
import com.library.domain.Book;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@ComponentScan(basePackageClasses = HibernateConfig.class)
public class AuthorManager implements AuthorDAO
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


    public List<Author> getAllAuthors()
    {
        return sessionFactory.getCurrentSession().getNamedQuery("author.all").list();
    }
    public  List<Book> getAuthorBooks(Author author)
    {
       return sessionFactory.getCurrentSession().get(Author.class, author.getIdAuthor()).getBooks();
    }
    public Author getAuthorById(Author author)
    {
        return sessionFactory.getCurrentSession().get(Author.class, author.getIdAuthor());
    }

    public List<Author> getAuthorBySurname(String surname)
    {
        return sessionFactory.getCurrentSession().getNamedQuery("author.bySurname").setString("surname", surname).list();
    }

    public Author updateAuthor(Author author)
    {
        return  (Author)sessionFactory.getCurrentSession().merge(author);
    }

    public void deleteAuthor(Author author)
    {
        sessionFactory.getCurrentSession().delete(author);
    }

    public Author addAuthor(Author author)
    {
        long idAuthor = ((Long)sessionFactory.getCurrentSession().save(author)).longValue();
        author.setIdAuthor(idAuthor);
        return author;
    }
    /*public void clearAuthors()
    {
        try
        {
            deleteAllAuthorsStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }*/

}
