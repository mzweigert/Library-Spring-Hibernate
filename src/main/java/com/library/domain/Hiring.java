package com.library.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "hiring.all", query = "Select h from Hiring h"),
        @NamedQuery(name = "hiring.byIdReader", query = "Select h from Hiring h where h.reader = :idReader"),
        @NamedQuery(name = "hiring.byIdBook", query = "Select h from Hiring h where h.book = :idBook")

})
public class Hiring implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idHiring;

    @ManyToOne
    @JoinColumn(name = "idBook")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "idReader")
    private Reader reader;

    @Column(nullable = false)
    private Date hireDate;

    public Hiring()
    {
    }

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

    public Date getHireDate()
    {
        return hireDate;
    }

    public void setHireDate(Date hireDate)
    {
        this.hireDate = hireDate;
    }

    public long getIdHiring()
    {
        return idHiring;
    }

    public void setIdHiring(long idHiring)
    {
        this.idHiring = idHiring;
    }

    @Override
    public String toString()
    {
        return "Hiring{" +
                "idHiring=" + idHiring +
                ", book=" + book +
                ", reader=" + reader +
                ", hireDate=" + hireDate +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Hiring)) return false;

        Hiring hiring = (Hiring) o;

        if (getIdHiring() != hiring.getIdHiring()) return false;
        if (!getBook().equals(hiring.getBook())) return false;
        if (!getReader().equals(hiring.getReader())) return false;
        return getHireDate().equals(hiring.getHireDate());

    }

    @Override
    public int hashCode()
    {
        int result = (int) (getIdHiring() ^ (getIdHiring() >>> 32));
        result = 31 * result + getBook().hashCode();
        result = 31 * result + getReader().hashCode();
        result = 31 * result + getHireDate().hashCode();
        return result;
    }
}
