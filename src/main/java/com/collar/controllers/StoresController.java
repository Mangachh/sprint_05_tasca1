package com.collar.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.collar.entities.Painting;
import com.collar.entities.Store;
import com.collar.services.PaintingRepoImpl;
import com.collar.services.StoreRepoImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoresController {
    /**
     * 
     * Crear botiga: li direm el nom i la capacitat (POST /shops/).
     * Llistar botigues: retorna la llista de botigues amb el seu nom i la capacitat
     * (GET /shops/).
     * Afegir quadre: li donarem el nom del quadre i el del autor (POST
     * /shops/{ID}/pictures)
     * Llistar els quadres de la botiga (GET /shops/{ID}/pictures).
     * Incendiar quadres: per si ve la policia, es poden eliminar tots els quadres
     * de la botiga sense deixar rastre (DELETE /shops/{ID}/pictures).
     */

    @Autowired
    private StoreRepoImpl stores;

    @Autowired
    private PaintingRepoImpl paintings;

    @PostConstruct
    //init de pruebas
    public void initStores() {
        Painting a = new Painting("First Painting", "MadDog", 547.24, LocalDate.of(2015, 7, 1));
        Painting b = new Painting("Second Painting", "Sanity Cat", 8777, LocalDate.of(1947, 2, 4));
        //List<Painting> paintings = new ArrayList<Painting>(List.of(a, b));

        Store store = new Store("First Store", 47);
        paintings.save(a);
        paintings.save(b);

        store.setPicture(a);
        stores.save(store);

        //paintings = new ArrayList<Painting>(List.of(a));

        store = new Store("Second Store", 4);
        store.setPicture(b);
        stores.save(store);

        store = new Store("Third Store", 27);
        stores.save(store);

    }

    /*listar els quadres de la botiga (GET /shops/{ID}/pictures).*/
    @GetMapping("/shops/{id}/pictures")
    public ResponseEntity<List<Painting>> showPictures(@PathVariable(name = "id") Long id) {
        List<Painting> pictures = stores.getPaintingsFromStore(id);

        if (pictures == null) {
            return ResponseEntity.badRequest().header("Not Found", "There's no shop with the id provided").body(null);
        }

        return new ResponseEntity<>(pictures, HttpStatus.OK);
    }

    /* Crear botiga: li direm el nom i la capacitat (POST /shops/). */
    @PostMapping("/shops/")
    public ResponseEntity<Store> createStore(@RequestParam(name = "name", required = false) final String name,
            @RequestParam(name = "capacity", required = false) final Integer capacity) {
        
        if (name == null) {
            return ResponseEntity.badRequest().header("Error", "No store name provided").body(null);
        }

        if (capacity == null) {
            return ResponseEntity.badRequest().header("Error", "No store capacity provided").body(null);
        }

        Store store = new Store(name, capacity);
        stores.save(store);
        return ResponseEntity.ok().body(store);
    }

    /*
     * Llistar botigues: retorna la llista de botigues amb el seu nom i la capacitat
     * (GET /shops/).
     */
    @GetMapping("/shops")
    private ResponseEntity<List<String>> getStores() {
        List<Store> listStores = stores.findAll();
        List<String> fixed = listStores.stream()
                .map(s -> "Store Name: " + s.getName() + "    Store Capacity: " + s.getMaxCapacity()).toList();
        return ResponseEntity.ok().body(fixed);
    }
    
    /*Incendiar quadres: per si ve la policia, es poden eliminar tots els quadres de la botiga sense deixar rastre (DELETE /shops/{ID}/pictures). */
    @DeleteMapping ("/shops/{id}/pictures")
    private ResponseEntity<String> deletePaintings(@PathVariable(name = "id") Long id) {

        Optional<Store> optStore = stores.findById(id);

        if (optStore.isPresent() == false) {
            return ResponseEntity.badRequest().header("Error", "No such store with the provided id")
                    .body("Without changes");
        }

        List<Painting> storePaintings = optStore.get().getPictures();
        paintings.removePaintings(storePaintings);
        storePaintings.clear();

        return ResponseEntity.ok().body("Operation Done");
    }
    
    /*Afegir quadre: li donarem el nom del quadre i el del autor (POST /shops/{ID}/pictures) */
    @PostMapping("/shops/{id}/pictures")
    private ResponseEntity<Painting> setPainting(@PathVariable(name = "id") final Long id, 
            @RequestParam(name = "name", required = false) final String name,
            @RequestParam(name = "artist", required = false) final String artist) {

        if (name == null) {
            return ResponseEntity.badRequest().header("Error", "No name provided").body(null);
        }

        if (artist == null) {
            return ResponseEntity.badRequest().header("Error", "No artist provided").body(null);
        }


        Painting painting = new Painting(name, artist);        
        Optional<Store> store = stores.findById(id);
        
        if (store.isPresent() == false) {
            return ResponseEntity.badRequest().header("Error", "No store with the provided id").body(null);
        }

        paintings.save(painting);
        store.get().setPicture(painting);
        stores.save(store.get());
        return ResponseEntity.ok().body(painting);
    }
}
