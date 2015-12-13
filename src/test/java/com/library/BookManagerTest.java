package com.library;


import com.library.domain.Author;
import com.library.domain.Book;
import com.library.domain.Hiring;
import com.library.domain.Reader;
import com.library.service.HiringManager;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(LibraryApplication.class)
@Rollback(value = false)
public class BookManagerTest
{
    @Autowired
    BookDAO bookManager;

    @Autowired
    AuthorDAO authorManager;

    @Autowired
    ReaderDAO readerManager;

    @Autowired
    HiringDAO hiringManager;

    Book book = new Book();

    @Test
    public void checkGettingSessionFactory()
    {
        SessionFactory sessionFactory = bookManager.getSessionFactory();

        assertNotNull(sessionFactory);
    }

    @Test
    public void checkAddingBook()
    {
        book = bookManager.addBook(new Book("Ferdydurke", Date.valueOf("1969-02-30"), 188));

        assertNotNull(book);
        assertEquals(book.getTitle(), "Ferdydurke");
        assertEquals(book.getRelaseDate(), Date.valueOf("1969-02-30"));
        assertEquals(book.getRelase(), 188);
        assertNotEquals(book.getIdBook(), 0);
    }

    @Test
    public void checkDeletingBook()
    {
        List<Book> booksBeforeChecking = bookManager.getAllBooks();

        book = bookManager.addBook(new Book("Ksiazeczka fajna bardzo", Date.valueOf("2011-01-02"), 122));


        int sizeOfAllBooksBeforeDeleting = bookManager.getAllBooks().size();
        bookManager.deleteBook(book);

        assertNull(bookManager.getBookById(book));
        assertNotEquals(sizeOfAllBooksBeforeDeleting, bookManager.getAllBooks().size());
        assertEquals(sizeOfAllBooksBeforeDeleting, bookManager.getAllBooks().size() + 1);

        List<Book> booksAfterChecking = bookManager.getAllBooks();

        for (Book b : booksAfterChecking)
        {
            assertTrue(booksBeforeChecking.contains(b));
        }
        for (Book b : booksBeforeChecking)
        {
            assertTrue(booksAfterChecking.contains(b));
        }

    }

    @Test
    public void checkUpdatingBook()
    {
        List<Book> booksBeforeChecking = bookManager.getAllBooks();

        book = bookManager.addBook(new Book("Folwark Zwierzecy", Date.valueOf("1993-03-05"), 155));

        book.setTitle("1984");
        book.setRelaseDate(Date.valueOf("1949-06-08"));
        book.setRelase(5);

        Book bookToTest = bookManager.updateBook(book);

        assertNotNull(book);
        assertNotNull(bookToTest);
        assertEquals(bookToTest.getTitle(), "1984");
        assertEquals(bookToTest.getRelaseDate(), Date.valueOf("1949-06-08"));
        assertEquals(bookToTest.getRelase(), 5);

        bookManager.deleteBook(bookToTest);

        List<Book> booksAfterChecking = bookManager.getAllBooks();


        for (Book b : booksAfterChecking)
        {
            assertTrue(booksBeforeChecking.contains(b));
        }
        for (Book b : booksBeforeChecking)
        {
            assertTrue(booksAfterChecking.contains(b));
        }

    }

    @Test
    public void checkGettingBookByIdWithAuthors()
    {
        Book first = bookManager.addBook(new Book("Tytus Romek i ten trzeci", Date.valueOf("2000-01-01"), 1));
        Book second = bookManager.addBook(new Book("Blok Ekipa", Date.valueOf("2000-01-01"), 1));

        List<Book> bookToAdd = new ArrayList<Book>();
        Author author;


        bookToAdd.add(first);

        author = new Author("Kolezka", "Smieszny");
        author.setBooks(bookToAdd);
        authorManager.addAuthor(author);

        author = new Author("Kazik", "Malomowny");
        author.setBooks(bookToAdd);
        authorManager.addAuthor(author);

        author = new Author("Wojtek", "Smieszek");
        author.setBooks(bookToAdd);
        authorManager.addAuthor(author);


        /*-----------------------------------------------------------------------*/


        bookToAdd.clear();
        bookToAdd.add(second);

        author = new Author("Kasia", "Kasikowska");
        author.setBooks(bookToAdd);
        authorManager.addAuthor(author);

        author = new Author("Spejson", "Spejson");
        author.setBooks(bookToAdd);
        authorManager.addAuthor(author);

        author = new Author("Walo", "Walo");
        author.setBooks(bookToAdd);
        authorManager.addAuthor(author);

        author = new Author("Wojtas", "Polonez");
        author.setBooks(bookToAdd);
        authorManager.addAuthor(author);


        long idTytusRomekITenTrzeci = bookManager.getBookById(first).getIdBook(); // 3 autorow
        long idBlokEkipa = bookManager.getBookById(second).getIdBook(); // 2 ksiazki


        first = new Book();
        second = new Book();
        book = new Book();

        //pobieramy jeszcze raz ksiazke Tytus Romek i ten trzeci bezposrednio z bazy po ID i sprawdzamy czy posiada autorow
        book.setIdBook(idTytusRomekITenTrzeci);
        first = bookManager.getBookByIdWithAuthors(book);


        //TESTY DLA 1 KSIAZKI
        assertNotNull(first);
        assertEquals(idTytusRomekITenTrzeci, first.getIdBook());
//        assertEquals(first.getAuthors().size(), 3);
        for (Author i : first.getAuthors())
        {
            assertNotNull(i);
        }


        //pobieramy jeszcze raz ksiazke Blok Ekipa bezposrednio z bazy po ID i sprawdzamy czy posiada autorow
        book.setIdBook(idBlokEkipa);
        second = bookManager.getBookByIdWithAuthors(book);

        //TESTY DLA 2 Ksiazki
        assertNotNull(second);
        assertEquals(idBlokEkipa, second.getIdBook());
        assertEquals(second.getAuthors().size(), 4);
        for (Author i : second.getAuthors())
        {
            assertNotNull(i);
        }
    }

    @Test
    public void checkGettingBookByTitle()
    {
        List<Book> books, booksToAdd = new ArrayList<Book>();

        //DODAJEMY KSIAZKI DO BAZY I JEDNOCZESNIE DO LOKALNEJ LISTY;
        booksToAdd.add(bookManager.addBook(new Book("Android programowanie", Date.valueOf("2014-07-21"), 5)));
        booksToAdd.add(bookManager.addBook(new Book("Android programowanie", Date.valueOf("2014-03-11"), 325)));
        booksToAdd.add(bookManager.addBook(new Book("Android programowanie", Date.valueOf("2014-01-21"), 22)));
        booksToAdd.add(bookManager.addBook(new Book("JAKAS KSIAZKA", Date.valueOf("2011-03-02"), 1555)));

        book = null;
        book = new Book();

        books = bookManager.getBookByTitle("Android programowanie");

        for (int i = 0; i < books.size(); i++)
        {
            assertEquals(books.get(i).getTitle(), "Android programowanie");
        }

        books = bookManager.getBookByTitle("JAKAS KSIAZKA");

        for (int i = 0; i < books.size(); i++)
        {
            assertEquals(books.get(i).getTitle(), "JAKAS KSIAZKA");
        }


    }

    @Test
    public void checkGettingBookById()
    {


        book = bookManager.addBook(new Book("Pan Tadeusz", Date.valueOf("2000-05-06"), 155));
        long idBook = book.getIdBook();

        book = null;
        assertNull(book);

        book = new Book();
        book.setIdBook(idBook);

        Book bookFoundById = bookManager.getBookById(book);

        assertEquals(bookFoundById.getIdBook(), idBook);
        assertEquals(bookFoundById.getTitle(), "Pan Tadeusz");
        assertEquals(bookFoundById.getRelaseDate(), Date.valueOf("2000-05-06"));
        assertEquals(bookFoundById.getRelase(), 155);

    }

    @Test
    public void checkGettingBookByIdWithHirings()
    {
        Reader reader;
        Hiring hiring;

        book = bookManager.addBook(new Book("Pan Tadeusz", Date.valueOf("2000-05-06"), 155));

        reader = readerManager.addReader(new Reader("Mate", "Zwee",  Date.valueOf("2002-02-16"), 100));
        hiring = hiringManager.addHiring(new Hiring(book, reader, Date.valueOf("2009-05-03")));

        reader = readerManager.addReader(new Reader("Kamil", "Liwinski",  Date.valueOf("2012-02-13"), 12));
        Hiring hiring2 = hiringManager.addHiring(new Hiring(book, reader, Date.valueOf("2009-02-03")));

        reader = readerManager.addReader(new Reader("Piotrek", "Piotrkowski",  Date.valueOf("2012-02-13"), 12));
        Hiring hiring3 = hiringManager.addHiring(new Hiring(book, reader, Date.valueOf("2011-02-03")));

        List<Hiring> bookHirings = bookManager.getBookByIdWithHirings(book).getHirings();

        assertEquals(bookHirings.size(), 3);
        assertTrue(bookHirings.contains(hiring));
        assertTrue(bookHirings.contains(hiring2));
        assertTrue(bookHirings.contains(hiring3));

    }

}

