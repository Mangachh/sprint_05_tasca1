package com.collar.repositories;

import com.collar.entities.Store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStoreRepo extends JpaRepository<Store, Long> {
    
}
