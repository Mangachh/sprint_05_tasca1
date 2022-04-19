package com.collar.repositories;

import com.collar.entities.Painting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaintingRepo extends JpaRepository<Painting, Long>{
    
}
