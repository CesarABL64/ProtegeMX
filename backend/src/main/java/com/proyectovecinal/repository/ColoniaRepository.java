package com.proyectovecinal.repository;

import com.proyectovecinal.entity.Colonia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColoniaRepository extends JpaRepository<Colonia, Integer> {

    List<Colonia> findByMunicipioIdMunicipio(Integer idMunicipio);
}
