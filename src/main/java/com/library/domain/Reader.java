package com.library.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
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

    @OneToMany(mappedBy = "reader")
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
}
