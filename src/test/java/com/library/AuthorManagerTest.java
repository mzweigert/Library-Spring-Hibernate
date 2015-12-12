package com.library;


import com.library.domain.Author;
import com.library.domain.Book;
import org.hibernate.SessionFactory;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(LibraryApplication.class)
@Rollback(value = false)
public class AuthorManagerTest
{
    @Autowired
    AuthorDAO authorManager;

    @Autowired
    BookDAO bookManager;


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
        author = authorManager.addAuthor(new Author("Andrzej", "Strzelba"));

        assertNotNull(author);
        assertEquals(author.getName(), "Andrzej");
        assertEquals(author.getSurname(), "Strzelba");
        assertNotEquals(author.getIdAuthor(), 0);
    }

    @Test
    public void checkDeletingAuthor()
    {
        List<Author> authorsBeforeChecking = authorManager.getAllAuthors();

        author = authorManager.addAuthor(new Author("Andrzej", "Strzelba"));

        int sizeBeforeDeleting = authorManager.getAllAuthors().size();

        authorManager.deleteAuthor(author);

        assertNull(authorManager.getAuthorById(author));
        assertNotEquals(sizeBeforeDeleting, authorManager.getAllAuthors().size());
        assertEquals(sizeBeforeDeleting, authorManager.getAllAuthors().size() + 1);

        List<Author> authorsAfterChecking = authorManager.getAllAuthors();

        for(Author a : authorsBeforeChecking)
            assertTrue(authorsAfterChecking.contains(a));

        for(Author a : authorsAfterChecking)
            assertTrue(authorsBeforeChecking.contains(a));


    }

    @Test
    public void checkUpdatingAuthor()
    {
        List<Author> authorsBeforeChecking = authorManager.getAllAuthors();

        author = authorManager.addAuthor(new Author("Mateusz", "Maklowicz"));
        author.setName("Robert");
        author.setSurname("Maklowiczowski");

        Author authorToTest = authorManager.updateAuthor(author);

        assertNotNull(author);
        assertNotNull(authorToTest);
        assertEquals(authorToTest.getIdAuthor(), author.getIdAuthor());
        assertNotEquals(authorToTest.getName(), "Mateusz");
        assertNotEquals(authorToTest.getSurname(), "Maklowicz");


        authorManager.deleteAuthor(authorToTest);

        List<Author> authorsAfterChecking = authorManager.getAllAuthors();

        for(Author a : authorsBeforeChecking)
            assertTrue(authorsAfterChecking.contains(a));

        for(Author a : authorsAfterChecking)
            assertTrue(authorsBeforeChecking.contains(a));



    }

    @Test
    public void checkGettingAuthorBooks()
    {

        Author first = new Author("Mateusz", "Strzelba");
        Author second = new Author("Witek", "Witkowski");

        List<Book> booksMateuszStrzelba = new ArrayList<Book>(), booksWitekWitkowski = new ArrayList<Book>();
        Book book;

        // DODAJEMY KSIAZKI DO LISTY PIERWSZEGO AUTORA
        book = bookManager.addBook(new Book("Tytus Romek i ten trzeci", Date.valueOf("2000-01-01"), 1));
        booksMateuszStrzelba.add(book);

        book = bookManager.addBook(new Book("fajna", Date.valueOf("2000-01-01"), 1));
        booksMateuszStrzelba.add(book);

        book = bookManager.addBook(new Book("nie fajna", Date.valueOf("2000-01-01"), 1));
        booksMateuszStrzelba.add(book);

        // DODAJEMY KSIAZKI DO LISTY DRUGIEGO AUTORA
        book = bookManager.addBook(new Book("slaba", Date.valueOf("2000-01-01"), 1));
        booksWitekWitkowski.add(book);

        book = bookManager.addBook(new Book("srednia", Date.valueOf("2000-01-01"), 1));
        booksWitekWitkowski.add(book);

        first.setBooks(booksMateuszStrzelba);
        second.setBooks(booksWitekWitkowski);

        //dodajemy autora do bazy i wyciagamy z niego ID
        long idMateuszStrzelba = authorManager.addAuthor(first).getIdAuthor(); // 3 ksiazki
        long idWitekWitkowski = authorManager.addAuthor(second).getIdAuthor(); // 2 ksiazki


        //Kasujemy autorow
        author = new Author();


        //pobieramy jeszcze raz autora Mateusz Strzelba bezposrednio z bazy po ID i sprawdzamy czy wszystko się zapisalo
        author.setIdAuthor(idMateuszStrzelba);
        first = authorManager.getAuthorByIdWithBooks(author);

        //TESTY DLA 1 AUTORA
        assertNotNull(first);
        assertEquals(author.getIdAuthor(), first.getIdAuthor());
//        assertEquals(first.getBooks().size(), 3);
        for (Book i : first.getBooks())
        {
            assertNotNull(i);
        }


        //pobieramy jeszcze raz autora Mateusz Strzelba bezposrednio z bazy po ID i sprawdzamy czy wszystko się zapisalo
        author.setIdAuthor(idWitekWitkowski);
        second = authorManager.getAuthorByIdWithBooks(author);

        //TESTY DLA 2 AUTORA
        assertNotNull(second);
        assertEquals(author.getIdAuthor(), second.getIdAuthor());
        assertEquals(second.getBooks().size(), 2);
        for (Book i : second.getBooks())
        {
            assertNotNull(i);
        }


    }

    @Test
    @Transactional
    public void checkGettingAuthorBySurname()
    {
        List<Author> authors;
        authorManager.addAuthor(new Author("Siwy", "Lewy"));
        authorManager.addAuthor(new Author("Czarny", "Lewy"));
        authorManager.addAuthor(new Author("Czerwony", "Lewy"));
        authorManager.addAuthor(new Author("Lis", "Czerwony"));
        authorManager.addAuthor(new Author("Biały", "Czerwony"));

        authors = authorManager.getAuthorBySurname("Lewy");


        for (int i = 0; i < authors.size(); i++)
        {
            assertEquals(authors.get(i).getSurname(), "Lewy");
        }


        authors = authorManager.getAuthorBySurname("Czerwony");


        for (int i = 0; i < authors.size(); i++)
        {
            assertEquals(authors.get(i).getSurname(), "Czerwony");
        }
    }

    @Test
    public void checkGettingAuthorById()
    {
        author = authorManager.addAuthor(new Author("Mariusz", "Buzianocnik"));
        long idAuthor = author.getIdAuthor();

        author = null;
        assertNull(author);

        author = new Author();
        author.setIdAuthor(idAuthor);
        Author authorFoundById = authorManager.getAuthorById(author);

        assertEquals(authorFoundById.getIdAuthor(), idAuthor);
        assertEquals(authorFoundById.getName(), "Mariusz");
        assertEquals(authorFoundById.getSurname(), "Buzianocnik");
    }


}
