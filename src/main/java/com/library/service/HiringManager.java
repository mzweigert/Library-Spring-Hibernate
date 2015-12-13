package com.library.service;



import com.library.HiringDAO;
import com.library.config.HibernateConfig;
import com.library.domain.Book;
import com.library.domain.Hiring;
import com.library.domain.Reader;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@ComponentScan(basePackageClasses = HibernateConfig.class)
public class HiringManager implements HiringDAO
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


    public List<Hiring> getAllHirings()
    {
        return sessionFactory.getCurrentSession().getNamedQuery("hiring.all").list();
    }

    public Hiring getHiringById(Hiring hiring)
    {
        return sessionFactory.getCurrentSession().get(Hiring.class, hiring.getIdHiring());
    }

    public List<Hiring> getHiringsByIdReader(Reader reader)
    {
        return sessionFactory.getCurrentSession().getNamedQuery("hiring.byIdReader").setLong("idReader", reader.getIdReader()).list();
    }

    public List<Hiring> getHiringsByIdBook(Book book)
    {
        return sessionFactory.getCurrentSession().getNamedQuery("hiring.byIdBook").setLong("idBook", book.getIdBook()).list();
    }

    public Hiring updateHiring(Hiring hiring)
    {
        return (Hiring) sessionFactory.getCurrentSession().merge(hiring);
    }

    public void deleteHiring(Hiring hiring)
    {
        sessionFactory.getCurrentSession().delete(hiring);
    }

    public Hiring addHiring(Hiring hiring)
    {
        long idHiring = ((Long)sessionFactory.getCurrentSession().save(hiring)).longValue();
        hiring.setIdHiring(idHiring);
        return hiring;
    }
}
