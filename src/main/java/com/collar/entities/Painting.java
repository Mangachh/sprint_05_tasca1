package com.collar.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity(name="paintings")
public class Painting {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String author;
    private double price;
    private LocalDate date;
    
    public Painting(){}

    public Painting(String name, String author, double price, LocalDate date) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.date = date;
    }

    public Painting(final String name, final String author) {
        this.name = name;
        this.author = author;
    }

    

    public Painting(Long id, String name, String author, double price, LocalDate date) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.price = price;
        this.date = date;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Picture [author=" + author + ", date=" + date + ", id=" + id + ", name=" + name + ", price=" + price
                + "]";
    }  

    @Override
    public boolean equals(final Object obj){
        if(obj == this){
            return true;
        }

        if(Painting.class.isInstance(obj) == false){
            return false;
        }

        Painting other = (Painting)obj;

        return other.getId() == this.id &&
               other.getName().equals(this.name) &&
               other.getAuthor().equals(this.author) &&
               other.getPrice() == this.price &&
               other.getDate().equals(this.date);        
    }

    

}
