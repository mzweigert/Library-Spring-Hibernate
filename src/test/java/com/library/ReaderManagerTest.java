package com.library;


import com.library.domain.Reader;
import org.hibernate.SessionFactory;
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
public class ReaderManagerTest
{
    @Autowired
    ReaderDAO readerManager;

    Reader reader = new Reader();

    @Test
    public void checkGettingSessionFactory()
    {
        SessionFactory sessionFactory = readerManager.getSessionFactory();

        assertNotNull(sessionFactory);
    }

    @Test
    public void checkAddingReader()
    {
        reader = readerManager.addReader(new Reader("Andrzej", "Strzelba", Date.valueOf("2015-01-01"), 20));

        assertNotNull(reader);
        assertEquals(reader.getName(), "Andrzej");
        assertEquals(reader.getSurname(), "Strzelba");
        assertEquals(reader.getJoinDate(), Date.valueOf("2015-01-01"));
        assertNotEquals(reader.getIdReader(), 20);
    }

    @Test
    public void checkDeletingReader()
    {
        List<Reader> readersBeforeChecking = readerManager.getAllReaders();

        reader = readerManager.addReader(new Reader("Andrzej", "Strzelba", Date.valueOf("2015-01-01"), 20));

        int sizeBeforeDeleting = readerManager.getAllReaders().size();

        readerManager.deleteReader(reader);

        assertNull(readerManager.getReaderById(reader));
        assertNotEquals(sizeBeforeDeleting, readerManager.getAllReaders().size());
        assertEquals(sizeBeforeDeleting, readerManager.getAllReaders().size() + 1);

        List<Reader> readersAfterChecking = readerManager.getAllReaders();

        for(Reader a : readersBeforeChecking)
            assertTrue(readersAfterChecking.contains(a));

        for(Reader a : readersAfterChecking)
            assertTrue(readersBeforeChecking.contains(a));


    }

    @Test
    public void checkUpdatingReader()
    {
        List<Reader> readersBeforeChecking = readerManager.getAllReaders();

        reader = readerManager.addReader(new Reader("ktos", "Zwege", Date.valueOf("2015-01-01"), 20));
        reader.setName("Mateusz");
        reader.setSurname("Zweigert");
        reader.setJoinDate(Date.valueOf("2001-02-06"));
        reader.setExtraPoints(50);

        Reader readerToTest = readerManager.updateReader(reader);

        assertNotNull(reader);
        assertNotNull(readerToTest);
        assertEquals(readerToTest.getIdReader(), reader.getIdReader());
        assertNotEquals(readerToTest.getName(), "ktos");
        assertNotEquals(readerToTest.getSurname(), "Zwege");
        assertNotEquals(readerToTest.getJoinDate(), Date.valueOf("2015-01-01"));
        assertNotEquals(readerToTest.getExtraPoints(), 20);
        assertEquals(readerToTest.getName(), "Mateusz");
        assertEquals(readerToTest.getSurname(), "Zweigert");
        assertEquals(readerToTest.getJoinDate(), Date.valueOf("2001-02-06"));
        assertEquals(readerToTest.getExtraPoints(), 50);


        readerManager.deleteReader(readerToTest);

        List<Reader> readersAfterChecking = readerManager.getAllReaders();

        for(Reader a : readersBeforeChecking)
            assertTrue(readersAfterChecking.contains(a));

        for(Reader a : readersAfterChecking)
            assertTrue(readersBeforeChecking.contains(a));



    }

    @Test
    public void checkGettingReaderBySurname()
    {
        List<Reader> readers;
        readerManager.addReader(new Reader("Siwy", "Lewy", Date.valueOf("2015-01-01"), 20));
        readerManager.addReader(new Reader("Czarny", "Lewy", Date.valueOf("2015-01-01"), 20));
        readerManager.addReader(new Reader("Czerwony", "Lewy", Date.valueOf("2015-01-01"), 20));
        readerManager.addReader(new Reader("Lis", "Czerwony", Date.valueOf("2015-01-01"), 20));
        readerManager.addReader(new Reader("Biały", "Czerwony", Date.valueOf("2015-01-01"), 20));

        readers = readerManager.getReadersBySurname("Lewy");


        for (int i = 0; i < readers.size(); i++)
        {
            assertEquals(readers.get(i).getSurname(), "Lewy");
        }


        readers = readerManager.getReadersBySurname("Czerwony");


        for (int i = 0; i < readers.size(); i++)
        {
            assertEquals(readers.get(i).getSurname(), "Czerwony");
        }
    }

    @Test
    public void checkGettingReaderById()
    {
        reader = readerManager.addReader(new Reader("Mariusz", "Buzianocnik", Date.valueOf("2015-01-01"), 20));
        long idReader = reader.getIdReader();

        reader = null;
        assertNull(reader);

        reader = new Reader();
        reader.setIdReader(idReader);
        Reader readerFoundById = readerManager.getReaderById(reader);

        assertEquals(readerFoundById.getIdReader(), idReader);
        assertEquals(readerFoundById.getName(), "Mariusz");
        assertEquals(readerFoundById.getSurname(), "Buzianocnik");
    }


}
