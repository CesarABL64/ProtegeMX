package com.proyectovecinal.repository;

import com.proyectovecinal.entity.CategoriaIncidente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaIncidenteRepository extends JpaRepository<CategoriaIncidente, Integer> {

    List<CategoriaIncidente> findByActiveTrue();
}
