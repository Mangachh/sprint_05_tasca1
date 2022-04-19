package com.collar.repositories;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.collar.entities.Painting;
import com.collar.entities.Store;
import com.collar.services.StoreRepoImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
@Transactional
public class StoreRepoImplTest {

    @Autowired
    private StoreRepoImpl repo;
    /*
        Crear botiga: li direm el nom i la capacitat (POST /shops/). HECHO!
        Llistar botigues: retorna la llista de botigues amb el seu nom i la capacitat (GET /shops/). HECHO TODO: hacer método que shoowee bien  
        Llistar els quadres de la botiga (GET /shops/{ID}/pictures). HECHO! 
        Incendiar quadres: per si ve la policia, es poden eliminar tots els quadres de la botiga sense deixar rastre (DELETE /shops/{ID}/pictures). 
    */

    @Test
    public void createStoreTest(){
        Store store = new Store("First Store", 47);
        repo.save(store);
        Optional<Store> found = repo.findById(store.getId());

        assertTrue(found.isPresent());
        assertEquals(store, found.get());
    }

    @Test
    public void getStores(){
        this.createManyStores();
        List<Store> stores = repo.findAll();

        assertNotNull(stores);
        assertEquals(4, stores.size());
    }

    @Test
    public void showPaintings(){
        
        Painting a = new Painting("First Painting", "MadDog", 547.24, LocalDate.of(2015, 7, 1));
        Painting b= new Painting("Second Painting", "Sanity Cat", 8777, LocalDate.of(1947, 2, 4));

        List<Painting> paintings = List.of(a,b);
        Store store = new Store("First Store", 47, paintings);
        repo.save(store);

        List<Painting> foundPaints = repo.getPaintingsFromStore(store.getId());
        assertNotNull(foundPaints);        
        assertEquals(paintings, foundPaints);
        System.out.println(foundPaints);
        
    }

    @Test
    public void deletePaintings(){
        Painting a = new Painting("First Painting", "MadDog", 547.24, LocalDate.of(2015, 7, 1));
        Painting b= new Painting("Second Painting", "Sanity Cat", 8777, LocalDate.of(1947, 2, 4));

        List<Painting> paintings = new ArrayList<Painting>(List.of(a,b));
        Store store = new Store("First Store", 47, paintings);
        repo.save(store);

        repo.deleteAllPaintings(store.getId());
        assertEquals(0, repo.getPaintingsFromStore(store.getId()).size());
    }

    private void createManyStores(){
        Store store = new Store("First Store", 47);
        repo.save(store);
        store = new Store("Second Store", 47);
        repo.save(store);
        store = new Store("Third Store", 47);
        repo.save(store);
        store = new Store("Fourth Store", 47);
        repo.save(store);
    }
}
