package com.collar.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.booleanThat;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.collar.entities.Painting;
import com.collar.entities.Store;
import com.collar.services.PaintingRepoImpl;
import com.collar.services.StoreRepoImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class StoresControllerTest {

    @Autowired
    private StoresController controller;

    @Test
    void testCreateStore() {
        String name = "Test 1";
        Integer capacity = 42;

        Store store = controller.createStore(name, capacity).getBody();     
        
        assertTrue(store != null);
        assertEquals(name, store.getName());
        assertEquals(capacity, store.getMaxCapacity());
    }

    @Test
    void testDeletePaintings() {
        Painting a = new Painting("A", "pepe");
        Painting b = new Painting("B", "maria");
        
        Store store = new Store("Test Paint", 12);        
        store.setPaintings(new ArrayList<Painting>(List.of(a, b)));

        controller.saveStore(store);
        
        controller.deletePaintings(store.getId());
        assertTrue(store.getPictures().size() == 0);
        assertTrue(controller.showPictures(store.getId()).getBody().size() == 0);
    }

    @Test
    void testGetStores() {
        Store storeA = controller.createStore("Test 1", 12).getBody();
        Store storeB = controller.createStore("Test 2", 24).getBody();
        List<String> stores = controller.getStores().getBody();

        boolean containsA = false;
        boolean containsB = false;

        // el ejercicio dice que sólo se tiene que mostrar nombre y capacidad,
        // asi que buscamos que en la lista esten los datos de la tienda A y B
        // ademas si tenemos init con diferentes tiendas por defecto esto no afectará 
        // a la prueba
        for (String st : stores) {  
            if(st.contains(storeA.getName()) && st.contains(String.valueOf(storeA.getMaxCapacity()))){
                containsA = true;
            }

            if(st.contains(storeB.getName()) && st.contains(String.valueOf(storeB.getMaxCapacity()))){
                containsB = true;
            }
        }

        assertTrue(containsA);
        assertTrue(containsB);
        
    }

    @Test
    void testSetPainting() {
        Store store = controller.createStore("Test 1", 6).getBody();

        Painting p = controller.setPainting(store.getId(), "Paint_1", "Pepote").getBody();
        Painting test = store.getPictures().get(0);

        assertEquals(p, test);
    }

    @Test
    void testShowPictures() {
        Store store = controller.createStore("Test 1", 6).getBody();

        Painting p1 = controller.setPainting(store.getId(), "Paint_1", "Pepote").getBody();
        Painting p2 = controller.setPainting(store.getId(), "Paint_2", "Mariela").getBody();

        List<Painting> paintings = controller.showPictures(store.getId()).getBody();

        assertTrue(paintings.size() == 2);
        assertTrue(paintings.contains(p1));
        assertTrue(paintings.contains(p2));
    }
}
