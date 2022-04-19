package com.collar.entities;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


@Entity(name="stores")
public class Store {
    
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    private String name;
    private int maxCapacity; 

    @OneToMany(
        cascade = CascadeType.MERGE,
        fetch = FetchType.EAGER
    )
    @JoinColumn(
        name ="store_id",
        referencedColumnName = "id"
    )
    private List<Painting> paintings;


    public Store(){}


    public Store(final String name, int maxCapacity) {
        this.name = name;
        this.maxCapacity = maxCapacity;  
        this.paintings = new ArrayList<>();
    }   

    public Store(final String name, int maxCapacity, final List<Painting> paintings){
        this.name = name;
        this.maxCapacity = maxCapacity;  
        this.paintings = paintings;
    }
    


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setPicture(final Painting painting) {
        this.paintings.add(painting);
    }

    public List<Painting> getPictures() {
        return this.paintings;
    }


    public void setPaintings(List<Painting> paintings) {
        this.paintings = paintings;
    }


    @Override
    public String toString() {
        return "Store [id=" + id + ", maxCapacity=" + maxCapacity + ", name=" + name + "]";
    }

    @Override
    public boolean equals(final Object obj){
        if(obj == this){
            return true;
        }

        if(Store.class.isInstance(obj) == false){
            return false;
        }

        Store other = (Store)obj;

        return other.getId() == this.id &&
               other.getName().equals(this.name) &&
               other.getMaxCapacity() == this.maxCapacity;   
    }

    
}
