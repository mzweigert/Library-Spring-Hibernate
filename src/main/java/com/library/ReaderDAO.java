package com.library;


import com.library.domain.Book;
import com.library.domain.Reader;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by MATEUSZ on 2015-10-18.
 */
public interface ReaderDAO
{
    SessionFactory getSessionFactory();
    List<Reader> getAllReaders();
    Reader getReaderById(Reader reader);
    List<Reader> getReadersBySurname(String surname);
    Reader getReaderByIdWithHirings(Reader reader);
    Reader updateReader(Reader reader);
    void deleteReader(Reader reader);
    Reader addReader(Reader reader);

}
