package com.library.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Author implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAuthor;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;

    @ManyToMany
    private List<Book> books = null;

    public Author()
    {

    }

    public Author(String name, String surname)
    {
        super();
        this.name = name;
        this.surname = surname;
    }
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public long getIdAuthor()
    {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor)
    {
        this.idAuthor = idAuthor;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

}
