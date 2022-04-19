package com.collar.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import com.collar.entities.Painting;
import com.collar.services.PaintingRepoImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class PaintingRepoImplTest {

    @Autowired
    private PaintingRepoImpl repo;
    
    /**
     *  Afegir quadre: li donarem el nom del quadre i el del autor (POST /shops/{ID}/pictures) 
    */
     
    @Test
    public void addPainting(){
        Painting paint = new Painting("Manzanas voladoras", "Manco de Lepanto", 542.24, LocalDate.of(1987, 04, 21));
        repo.save(paint);
        Optional<Painting> found = repo.findById(paint.getId());

        assertTrue(found.isPresent());
        assertEquals(paint, found.get());
    }
    
}
