package com.library.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "Book")
public class Book implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idBook;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Date relaseDate;
    @Column(nullable = false)
    private int relase;

    @OneToMany(mappedBy = "book")
    private List<Hiring> hirings;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "books")
    private List<Author> authors;

    public Book()
    {

    }

    public Book(String title, Date relaseDate, int relase)
    {
        super();
        this.title = title;
        this.relaseDate = relaseDate;
        this.relase = relase;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Date getRelaseDate()
    {
        return relaseDate;
    }

    public void setRelaseDate(Date relaseDate)
    {
        this.relaseDate = relaseDate;
    }

    public int getRelase()
    {
        return relase;
    }

    public void setRelase(int relase)
    {
        this.relase = relase;
    }

    public long getIdBook()
    {
        return idBook;
    }

    public void setIdBook(long idBook)
    {
        this.idBook = idBook;
    }

    public List<Hiring> getHirings()
    {
        return hirings;
    }

    public void setHirings(List<Hiring> hirings)
    {
        this.hirings = hirings;
    }

    public List<Author> getAuthors()
    {
        return authors;
    }

    public void setAuthors(List<Author> authors)
    {
        this.authors = authors;
    }


}
