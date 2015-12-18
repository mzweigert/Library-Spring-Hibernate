package com.library;


import com.library.domain.Author;
import com.library.domain.Book;
import com.library.domain.Reader;
import com.library.domain.Hiring;
import com.library.service.BookManager;

import com.library.service.HiringManager;

import com.library.service.ReaderManager;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(LibraryApplication.class)
@Rollback(value = false)
public class HiringManagerTest
{

    @Autowired
    BookDAO bookManager;

    @Autowired
    ReaderDAO readerManager;

    @Autowired
    HiringDAO hiringManager;

    Book book;
    Reader reader;
    Hiring hiring;

	Book firstToDeleteBook, secondToDeleteBook, thirdToDeleteBook;
	Reader firstToDeleteReader, secondToDeleteReader, thirdToDeleteReader;
	Hiring firstToDeleteHiring, secondToDeleteHiring, thirdToDeleteHiring;
	
	@Before
	public void addExamplesHirings()
	{
		firstToDeleteBook = bookManager.addBook(new Book("Ferdydurke", Date.valueOf("1969-02-30"), 188));
		firstToDeleteReader = readerManager.addReader(new Reader("Mateusz", "Zweigert", Date.valueOf("2011-01-01"), 11));
		firstToDeleteHiring = hiringManager.addHiring(new Hiring(firstToDeleteBook, firstToDeleteReader, Date.valueOf("2015-10-12")));
		
		secondToDeleteBook = bookManager.addBook(new Book("Ferdydurke2", Date.valueOf("1969-02-30"), 188));
		secondToDeleteReader = readerManager.addReader(new Reader("Mateusz2", "Zweigert", Date.valueOf("2011-01-01"), 11));
		secondToDeleteHiring = hiringManager.addHiring(new Hiring(secondToDeleteBook, secondToDeleteReader, Date.valueOf("2015-10-12")));
		
		thirdToDeleteBook = bookManager.addBook(new Book("Ferdydurke3", Date.valueOf("1969-02-30"), 188));
		thirdToDeleteReader = readerManager.addReader(new Reader("Mateusz3", "Zweigert", Date.valueOf("2011-01-01"), 11));
		thirdToDeleteHiring = hiringManager.addHiring(new Hiring(thirdToDeleteBook, thirdToDeleteReader, Date.valueOf("2015-10-12")));
		
		
	}
	@After
	public void deleteExamplesHirings()
	{
	
		hiringManager.deleteHiring(firstToDeleteHiring);
		hiringManager.deleteHiring(secondToDeleteHiring);
		hiringManager.deleteHiring(thirdToDeleteHiring);
	}
	
    @Test
    public void checkGettingSessionFactory()
    {
        SessionFactory sessionFactory = bookManager.getSessionFactory();

        assertNotNull(sessionFactory);
    }

    @Test
    public void checkAddingHiring()
    {
        book = bookManager.addBook(new Book("Fajna", Date.valueOf("2015-01-01"), 1));

        assertNotNull(book);
        assertEquals(book.getTitle(), "Fajna");
        assertEquals(book.getRelaseDate(), Date.valueOf("2015-01-01"));
        assertEquals(book.getRelase(), 1);

        reader = readerManager.addReader(new Reader("Mateusz", "Zweigert", Date.valueOf("2011-01-01"), 11));

        assertNotNull(reader);
        assertEquals(reader.getName(), "Mateusz");
        assertEquals(reader.getSurname(), "Zweigert");
        assertEquals(reader.getJoinDate(), Date.valueOf("2011-01-01") );
        assertEquals(reader.getExtraPoints(), 11);

        hiring = hiringManager.addHiring(new Hiring(book, reader, Date.valueOf("2015-10-12")));


        assertNotNull(hiring);
        assertEquals(hiring.getBook(), book);
        assertEquals(hiring.getReader(), reader);
        assertEquals(hiring.getHireDate(), Date.valueOf("2015-10-12"));

    }

    @Test
    public void checkDeletingHiring()
    {
        List<Hiring> hiringsBeforeChecking = hiringManager.getAllHirings();

        book = bookManager.addBook(new Book("Fajna", Date.valueOf("2015-01-01"), 1));

        assertNotNull(book);
        assertEquals(book.getTitle(), "Fajna");
        assertEquals(book.getRelaseDate(), Date.valueOf("2015-01-01"));
        assertEquals(book.getRelase(), 1);

        reader = readerManager.addReader(new Reader("Mateusz", "Zweigert", Date.valueOf("2011-01-01"), 11));

        assertNotNull(reader);
        assertEquals(reader.getName(), "Mateusz");
        assertEquals(reader.getSurname(), "Zweigert");
        assertEquals(reader.getJoinDate(), Date.valueOf("2011-01-01") );
        assertEquals(reader.getExtraPoints(), 11);

        hiring = hiringManager.addHiring(new Hiring(book, reader, Date.valueOf("2015-10-12")));

        int sizeBeforeDeleting = hiringManager.getAllHirings().size();

        hiringManager.deleteHiring(hiring);

        assertNull(hiringManager.getHiringById(hiring));
        assertNotEquals(sizeBeforeDeleting, hiringManager.getAllHirings().size());
        assertEquals(sizeBeforeDeleting, hiringManager.getAllHirings().size() + 1);

        List<Hiring> hiringsAfterChecking = hiringManager.getAllHirings();

        for(Hiring h : hiringsBeforeChecking)
            assertTrue(hiringsAfterChecking.contains(h));

        for(Hiring h : hiringsAfterChecking)
            assertTrue(hiringsBeforeChecking.contains(h));



    }
    @Test
    public void checkUpdatingHiring()
    {
        ///////////////////////////////////////////////////////////////////
        // DODAWANIE REKORDOW BOOK I READER , ORAZ DODANIE 1 REKORDU HIRING KTOREGO BEDZIEMY UPDATE'OWAC
        book = bookManager.addBook(new Book("Taka sobie", Date.valueOf("2015-01-01"), 263));
        bookManager.addBook(new Book("Spoko", Date.valueOf("2015-01-01"), 234));

        reader = readerManager.addReader(new Reader("Andrzej", "Koles", Date.valueOf("2015-10-20"), 2000));



        hiring = hiringManager.addHiring(new Hiring(book, reader, Date.valueOf("2015-09-09")));
        //////////////////////////////////////////////////////////////////////

        // poki co nasz rekord zapisany w hiring ma pole idBook ksi?zki o tytule "Taka sobie".
        //Zmienmy mu zatem idKsiazki "Kiepska"
        // idAuthora obecnie to Andrzej koles, zmienmy mu na autora Mateusz Zweigert
        book = bookManager.addBook(new Book("Kiepska", Date.valueOf("2015-01-01"), 236));
        reader = readerManager.addReader(new Reader("Mateusz", "Zweigert", Date.valueOf("2015-10-20"), 2000));

        hiring.setBook(book);
        hiring.setReader(reader);
        //DATE WYPOZYCZENIA TEZ ZMIENMY
        hiring.setHireDate(Date.valueOf("2002-02-02"));

        // Update'ujemy hiring ze zmienionym book i hiring

        Hiring hiringToTest = hiringManager.updateHiring(hiring);

        //sprawdzamy czy idBook rekordu bezposrednio pobranego z bazy jest roowne idBook ksiazki "Kiepska"
        assertEquals(hiringToTest.getBook(), book);
        //sprawdzamy czy idReader rekordu bezposrednio pobranego z bazy jest roowne idReader czytelnika "Mateusz Zweigert"
        assertEquals(hiringToTest.getReader(), reader);
        assertEquals(hiringToTest.getHireDate(), Date.valueOf("2002-02-02"));
    }

    @Test
    public void checkGettingHiringById()
    {
        Hiring hiringFromDataBaseById;
        ///////////////////////////////////////////////////////////////////
        // DODAWANIE REKORDuBOOK I READER , ORAZ DODANIE 1 REKORDU HIRING z ktorego bedziemy korzystac
        book = bookManager.addBook(new Book("Spoko", Date.valueOf("2015-01-01"), 234));
        reader = readerManager.addReader(new Reader("Andrzej", "Koles", Date.valueOf("2015-10-20"), 2000));
        hiring = hiringManager.addHiring(new Hiring(book, reader, Date.valueOf("2015-09-09")));
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //pobieramy rekord hiring z bazy ktory dodalismy
        hiringFromDataBaseById = hiringManager.getHiringById(hiring);

        //ponownie go pobieramy tym razem przez odwolanie sie do jego id i przypisujemy do zmiennej w celu pozniejszego porownania ich wartosci
        hiringFromDataBaseById = hiringManager.getHiringById(hiring);

        assertEquals(hiring.getIdHiring(), hiringFromDataBaseById.getIdHiring());
        assertEquals(hiring.getBook(), hiringFromDataBaseById.getBook());
        assertEquals(hiring.getReader(), hiringFromDataBaseById.getReader());
        assertEquals(hiring.getHireDate(), hiringFromDataBaseById.getHireDate());
    }

    @Test
    public void checkGettingHiringsByIdReader()
    {

        //Dodajemy czytelnika, nastepnie ksiązke i kilka zamowien dla tego czytelnika
        reader = readerManager.addReader(new Reader("Andrzej", "Koles", Date.valueOf("2015-10-20"), 2000));


        book = bookManager.addBook(new Book("Taka sobie", Date.valueOf("2015-01-01"), 1));
        hiring = hiringManager.addHiring(new Hiring(book, reader, Date.valueOf("2015-01-01")));

        book = bookManager.addBook(new Book("Fajna", Date.valueOf("2015-01-01"), 1));
        Hiring hiring2 = hiringManager.addHiring(new Hiring(book, reader, Date.valueOf("2015-01-01")));

        List<Hiring> hiringsByIdReader = hiringManager.getHiringsByIdReader(reader);

        assertEquals(hiring.getReader(), reader);
        assertEquals(readerManager.getReaderByIdWithHirings(reader).getHirings().size(), hiringsByIdReader.size());

        for(Hiring h : readerManager.getReaderByIdWithHirings(reader).getHirings())
            assertTrue(hiringsByIdReader.contains(h));

        for(Hiring h : hiringsByIdReader)
            assertTrue(readerManager.getReaderByIdWithHirings(reader).getHirings().contains(h));

    }

    @Test
    public void checkGettingHiringByIdBook()
    {

        //Dodajemy czytelnika, nastepnie ksiązke i kilka zamowien dla tego czytelnika
        book = bookManager.addBook(new Book("Jak polubic kluski slaskie?", Date.valueOf("2015-01-01"), 1));

        reader = readerManager.addReader(new Reader("Andrzej", "Koles", Date.valueOf("2015-10-20"), 2000));
        hiring = hiringManager.addHiring(new Hiring(book, reader, Date.valueOf("2015-01-01")));

        reader = readerManager.addReader(new Reader("Maly", "czlowiek", Date.valueOf("2015-10-20"), 1000));
        Hiring hiring2 = hiringManager.addHiring(new Hiring(book, reader, Date.valueOf("2015-01-01")));

        List<Hiring> hiringsByIdBook = hiringManager.getHiringsByIdBook(book);

        assertEquals(hiring.getBook(), book);
        assertEquals(bookManager.getBookByIdWithHirings(book).getHirings().size(), hiringsByIdBook.size());

        for(Hiring h : bookManager.getBookByIdWithHirings(book).getHirings())
            assertTrue(hiringsByIdBook.contains(h));

        for(Hiring h : hiringsByIdBook)
            assertTrue(bookManager.getBookByIdWithHirings(book).getHirings().contains(h));



    }


}

