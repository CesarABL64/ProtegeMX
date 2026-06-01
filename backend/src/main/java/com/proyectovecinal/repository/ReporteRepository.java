package com.proyectovecinal.repository;

import com.proyectovecinal.entity.Reporte;
import com.proyectovecinal.entity.enums.EstatusReporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Integer> {

    List<Reporte> findByEstatus(EstatusReporte estatus);

    List<Reporte> findByColoniaIdColonia(Integer idColonia);

    List<Reporte> findByEstatusAndColoniaIdColonia(EstatusReporte estatus, Integer idColonia);

    List<Reporte> findByUsuarioIdUsuario(Integer idUsuario);

    List<Reporte> findBySubcategoriaCategoriaEsCriticoTrueAndEstatus(EstatusReporte estatus);
}
