package com.collar.services;

import java.util.List;
import java.util.Optional;

import com.collar.entities.Painting;
import com.collar.repositories.IPaintingRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaintingRepoImpl {

    @Autowired
    private IPaintingRepo repo;

    public Painting save(final Painting painting) {
        return repo.save(painting);
    }
    
    public void removePaintings(final List<Painting> paintings) {
        for (Painting p : paintings) {
            repo.delete(p);
        }
    }

    public Optional<Painting> findById(final Long id) {
        return repo.findById(id);
    }
    
}
