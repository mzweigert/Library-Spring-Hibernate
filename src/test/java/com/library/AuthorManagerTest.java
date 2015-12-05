package com.library;

import com.library.config.HibernateConfig;
import com.library.domain.Author;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(LibraryApplication.class)
@Rollback(value = true)
@Transactional
public class AuthorManagerTest
{
    @Autowired
    AuthorDAO authorManager;


    Author author = new Author();

    @Test
    public void checkGettingSessionFactory()
    {
        SessionFactory sessionFactory = authorManager.getSessionFactory();

        assertNotNull(sessionFactory);
    }

    @Test
    public void checkAddingAuthor()
    {
        author = authorManager.addAuthor( new Author("Andrzej", "Strzelba"));

        assertNotNull(author);
        assertEquals(author.getName(), "Andrzej");
        assertEquals(author.getSurname(), "Strzelba");
        assertNotEquals(author.getIdAuthor(), 0);
    }
    @Test
    public void checkDeletingAuthor()
    {
        authorManager.addAuthor(new Author("Mateusz", "Strzelba"));
        author = authorManager.addAuthor(new Author("Andrzej", "Strzelba"));

        int sizeBeforeDeleting  = authorManager.getAllAuthors().size();

        authorManager.deleteAuthor(author);

        assertNull(authorManager.getAuthorById(author));
        assertNotEquals(sizeBeforeDeleting, authorManager.getAllAuthors().size());

    }
    @Test
    public void checkUpdatingAuthor()
    {
        Author author = authorManager.addAuthor(new Author("Mateusz", "Maklowicz"));
        author.setName("Robert");
        author.setSurname("Maklowiczowski");

        Author authorToTest = authorManager.updateAuthor(author);

        assertNotNull(author);
        assertNotNull(authorToTest);
        assertEquals(authorToTest.getIdAuthor(), author.getIdAuthor());
        assertNotEquals(authorToTest.getName(), "Mateusz");
        assertNotEquals(authorToTest.getSurname(), "Maklowiczr");

    }

    @Test
    public void checkGettingAuthorBySurname()
    {
        List<Author> authors;
        authorManager.addAuthor( new Author("Siwy", "Lewy"));
        authorManager.addAuthor(new Author("Czarny", "Lewy"));
        authorManager.addAuthor(new Author("Czerwony", "Lewy"));
        authorManager.addAuthor(new Author("Lis", "Czerwony"));
        authorManager.addAuthor(new Author("Biały", "Czerwony"));

        authors = authorManager.getAuthorBySurname("Lewy");

        assertEquals(authors.size(), 3);

        for(int i= 0; i<authors.size() ; i++)
            assertEquals(authors.get(i).getSurname(), "Lewy");



        authors = authorManager.getAuthorBySurname("Czerwony");

        assertEquals(authors.size(), 2);

        for(int i= 0; i<authors.size() ; i++)
            assertEquals(authors.get(i).getSurname(), "Czerwony");
    }

    @Test
    public void checkGettingAuthorById()
    {
        Author author = authorManager.addAuthor(new Author("Mariusz", "Buzianocnik"));
        long idAuthor  = author.getIdAuthor();

        author = null;
        assertNull(author);

        author = new Author();
        author.setIdAuthor(idAuthor);
        Author authorFoundById  = authorManager.getAuthorById(author);

        assertEquals(authorFoundById.getIdAuthor(), idAuthor);
        assertEquals(authorFoundById.getName(), "Mariusz");
        assertEquals(authorFoundById.getSurname(), "Buzianocnik");
    }


}
