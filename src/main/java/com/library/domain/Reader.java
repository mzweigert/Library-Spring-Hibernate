package com.library.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "reader.all", query = "Select r from Reader r"),
        @NamedQuery(name = "reader.bySurname", query = "Select r from Reader r where r.surname = :surname")
})
@Table(name = "Reader")
public class Reader implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idReader;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private Date joinDate;
    @Column(nullable = false)
    private int extraPoints;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "reader")
    private List<Hiring> hirings;

    public Reader()
    {

    }

    public Reader(String name, String surname, Date joinDate, int extraPoints)
    {

        this.name = name;
        this.surname = surname;
        this.joinDate = joinDate;
        this.extraPoints = extraPoints;
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

    public Date getJoinDate()
    {
        return joinDate;
    }

    public void setJoinDate(Date joinDate)
    {
        this.joinDate = joinDate;
    }

    public int getExtraPoints()
    {
        return extraPoints;
    }

    public void setExtraPoints(int extraPoints)
    {
        this.extraPoints = extraPoints;
    }

    public long getIdReader()
    {
        return idReader;
    }

    public void setIdReader(long idReader)
    {
        this.idReader = idReader;
    }

    public List<Hiring> getHirings()
    {
        return hirings;
    }

    public void setHirings(List<Hiring> hirings)
    {
        this.hirings = hirings;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Reader)) return false;

        Reader reader = (Reader) o;

        if (getIdReader() != reader.getIdReader()) return false;
        if (getExtraPoints() != reader.getExtraPoints()) return false;
        if (!getName().equals(reader.getName())) return false;
        if (!getSurname().equals(reader.getSurname())) return false;
        return (getJoinDate().equals(reader.getJoinDate()));

    }

    @Override
    public int hashCode()
    {
        int result = (int) (getIdReader() ^ (getIdReader() >>> 32));
        result = 31 * result + getName().hashCode();
        result = 31 * result + getSurname().hashCode();
        result = 31 * result + getJoinDate().hashCode();
        result = 31 * result + getExtraPoints();
        result = 31 * result + (getHirings() != null ? getHirings().hashCode() : 0);
        return result;
    }
}
