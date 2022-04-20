package com.collar.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.collar.entities.Painting;
import com.collar.entities.Store;
import com.collar.repositories.IStoreRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreRepoImpl {

    @Autowired
    private IStoreRepo repo;    

    public StoreRepoImpl(final IStoreRepo repo) {
        this.repo = repo;
    }

    public StoreRepoImpl(){}

    public Store save(final Store store) {
        return repo.save(store);
    }

    public Optional<Store> findById(final Long id) {
        return repo.findById(id);
    }

    public List<Store> findAll() {
        return repo.findAll();
    }

    public List<Painting> getPaintingsFromStore(final Long id) {
        Optional<Store> st = repo.findById(id);

        if (st.isPresent()) {
            return st.get().getPictures();
        }

        return null;
    }
   

}
