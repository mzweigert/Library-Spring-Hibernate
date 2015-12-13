package com.library.service;



import com.library.ReaderDAO;
import com.library.config.HibernateConfig;
import com.library.domain.Book;
import com.library.domain.Reader;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@ComponentScan(basePackageClasses = HibernateConfig.class)
public class ReaderManager implements ReaderDAO
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

    public List<Reader> getAllReaders()
    {
        return sessionFactory.getCurrentSession().getNamedQuery("reader.all").list();
    }

    public Reader getReaderById(Reader reader)
    {
        return sessionFactory.getCurrentSession().get(Reader.class, reader.getIdReader());
    }

    public List<Reader> getReadersBySurname(String surname)
    {
        return sessionFactory.getCurrentSession().getNamedQuery("reader.bySurname").setString("surname", surname).list();
    }

    public Reader getReaderByIdWithHirings(Reader reader)
    {
        Reader readerWithHirings = sessionFactory.getCurrentSession().get(Reader.class, reader.getIdReader());
        Hibernate.initialize(readerWithHirings.getHirings());

        return readerWithHirings;
    }

    public Reader updateReader(Reader reader)
    {
        return (Reader) sessionFactory.getCurrentSession().merge(reader);

    }

    public void deleteReader(Reader reader)
    {
        sessionFactory.getCurrentSession().delete(reader);
    }

    public Reader addReader(Reader reader)
    {
        long idReader = ((Long)sessionFactory.getCurrentSession().save(reader)).longValue();
        reader.setIdReader(idReader);
        return reader;
    }
}
