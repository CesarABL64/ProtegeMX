package com.proyectovecinal.repository;

import com.proyectovecinal.entity.SubcategoriaIncidente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategoriaIncidenteRepository extends JpaRepository<SubcategoriaIncidente, Integer> {

    List<SubcategoriaIncidente> findByCategoriaIdCategoriaAndActivoTrue(Integer idCategoria);

    List<SubcategoriaIncidente> findByActivoTrue();
}
