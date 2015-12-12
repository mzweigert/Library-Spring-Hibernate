package com.library.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "author.all", query = "Select a from Author a"),
        @NamedQuery(name = "author.bySurname", query = "Select a from Author a where a.surname = :surname")
})
@Table(name = "Author")
public class Author implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAuthor;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "Author_Book", joinColumns = {@JoinColumn(name = "idAuthor")}, inverseJoinColumns = {@JoinColumn(name = "idBook")})
    private List<Book> books = null;

    public Author()
    {

    }

    public Author(String name, String surname)
    {
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

    public void setIdAuthor(long idAuthor)
    {
        this.idAuthor = idAuthor;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString()
    {
        return "Author{" +
                "idAuthor=" + idAuthor +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;

        Author author = (Author) o;

        if (getIdAuthor() != author.getIdAuthor()) return false;
        if (!getName().equals(author.getName())) return false;
        return (getSurname().equals(author.getSurname()));

    }

    @Override
    public int hashCode()
    {
        int result = (int) (getIdAuthor() ^ (getIdAuthor() >>> 32));
        result = 31 * result + getName().hashCode();
        result = 31 * result + getSurname().hashCode();
        result = 31 * result + getBooks().hashCode();
        return result;
    }
}
