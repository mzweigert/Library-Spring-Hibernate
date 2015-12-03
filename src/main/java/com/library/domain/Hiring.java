package com.library.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class Hiring implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idHiring;

    @ManyToOne
    @JoinColumn(name="idBook")
    private Book book;

    @ManyToOne
    @JoinColumn(name="idReader")
    private Reader reader;

    @Column(nullable = false)
    private Date hireDate;

    public Hiring() {}

    public Hiring(Book book, Reader reader, Date hireDate)
    {
        super();
        this.book = book;
        this.reader = reader;
        this.hireDate = hireDate;
    }

    public Book getBook()
    {
        return book;
    }

    public void setBook(Book book)
    {
        this.book = book;
    }

    public Reader getReader()
    {
        return reader;
    }

    public void setReader(Reader reader)
    {
        this.reader = reader;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public long getIdHiring() {
        return idHiring;
    }

    public void setIdHiring(int idHiring) {
        this.idHiring = idHiring;
    }

}
