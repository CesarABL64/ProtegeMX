package com.proyectovecinal.service;

import com.proyectovecinal.dto.ReporteRequestDTO;
import com.proyectovecinal.dto.ReporteResponseDTO;
import com.proyectovecinal.entity.Colonia;
import com.proyectovecinal.entity.Reporte;
import com.proyectovecinal.entity.SubcategoriaIncidente;
import com.proyectovecinal.entity.Usuario;
import com.proyectovecinal.entity.enums.EstatusReporte;
import com.proyectovecinal.repository.ColoniaRepository;
import com.proyectovecinal.repository.ReporteRepository;
import com.proyectovecinal.repository.SubcategoriaIncidenteRepository;
import com.proyectovecinal.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ReporteService {

    private final ReporteRepository reporteRepository;
    private final UsuarioRepository usuarioRepository;
    private final SubcategoriaIncidenteRepository subcategoriaIncidenteRepository;
    private final ColoniaRepository coloniaRepository;
    private final EmailService emailService;

    public ReporteService(ReporteRepository reporteRepository,
                          UsuarioRepository usuarioRepository,
                          SubcategoriaIncidenteRepository subcategoriaIncidenteRepository,
                          ColoniaRepository coloniaRepository,
                          EmailService emailService) {
        this.reporteRepository = reporteRepository;
        this.usuarioRepository = usuarioRepository;
        this.subcategoriaIncidenteRepository = subcategoriaIncidenteRepository;
        this.coloniaRepository = coloniaRepository;
        this.emailService = emailService;
    }

    @Transactional
    public ReporteResponseDTO crear(ReporteRequestDTO dto, Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        SubcategoriaIncidente subcategoria = subcategoriaIncidenteRepository.findById(dto.getIdSubcategoria())
                .orElseThrow(() -> new RuntimeException("Subcategoría no encontrada"));

        Colonia colonia = coloniaRepository.findById(dto.getIdColonia())
                .orElseThrow(() -> new RuntimeException("Colonia no encontrada"));

        Reporte reporte = new Reporte();
        reporte.setUsuario(usuario);
        reporte.setSubcategoria(subcategoria);
        reporte.setColonia(colonia);
        reporte.setDescripcion(dto.getDescripcion());
        reporte.setLat(dto.getLat());
        reporte.setLng(dto.getLng());
        reporte.setDireccionAprox(dto.getDireccionAprox());
        reporte.setNivelUrgencia(dto.getNivelUrgencia());
        reporte.setAnonimo(dto.getAnonimo());

        Reporte saved = reporteRepository.save(reporte);

        emailService.notificarReporteCritico(saved);

        return toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public ReporteResponseDTO obtenerPorId(Integer id) {
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado con ID: " + id));
        return toResponseDTO(reporte);
    }

    @Transactional(readOnly = true)
    public Page<ReporteResponseDTO> listar(Pageable pageable, EstatusReporte estatus, Integer idColonia) {
        if (estatus != null && idColonia != null) {
            return reporteRepository.findByEstatusAndColoniaIdColonia(estatus, idColonia)
                    .stream().map(this::toResponseDTO)
                    .collect(java.util.stream.Collectors.collectingAndThen(
                            java.util.stream.Collectors.toList(),
                            list -> new org.springframework.data.domain.PageImpl<>(list, pageable, list.size())
                    ));
        }
        if (estatus != null) {
            return reporteRepository.findByEstatus(estatus)
                    .stream().map(this::toResponseDTO)
                    .collect(java.util.stream.Collectors.collectingAndThen(
                            java.util.stream.Collectors.toList(),
                            list -> new org.springframework.data.domain.PageImpl<>(list, pageable, list.size())
                    ));
        }
        if (idColonia != null) {
            return reporteRepository.findByColoniaIdColonia(idColonia)
                    .stream().map(this::toResponseDTO)
                    .collect(java.util.stream.Collectors.collectingAndThen(
                            java.util.stream.Collectors.toList(),
                            list -> new org.springframework.data.domain.PageImpl<>(list, pageable, list.size())
                    ));
        }
        return reporteRepository.findAll(pageable).map(this::toResponseDTO);
    }

    @Transactional
    public ReporteResponseDTO actualizarEstatus(Integer id, EstatusReporte nuevoEstatus) {
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado con ID: " + id));

        EstatusReporte estatusAnterior = reporte.getEstatus();
        reporte.setEstatus(nuevoEstatus);
        reporte.setUpdatedAt(LocalDateTime.now());

        if (nuevoEstatus == EstatusReporte.RESUELTO) {
            reporte.setResueltoAt(LocalDateTime.now());
        }

        Reporte saved = reporteRepository.save(reporte);

        emailService.notificarCambioEstatus(saved, estatusAnterior);

        return toResponseDTO(saved);
    }

    @Transactional
    public void eliminar(Integer id) {
        if (!reporteRepository.existsById(id)) {
            throw new RuntimeException("Reporte no encontrado con ID: " + id);
        }
        reporteRepository.deleteById(id);
    }

    private ReporteResponseDTO toResponseDTO(Reporte reporte) {
        return ReporteResponseDTO.builder()
                .idReporte(reporte.getIdReporte())
                .nombreUsuario(reporte.getAnonimo() ? "Anónimo" : reporte.getUsuario().getNombre())
                .categoriaIncidente(reporte.getSubcategoria().getCategoria().getNombre())
                .subcategoria(reporte.getSubcategoria().getNombre())
                .colonia(reporte.getColonia().getNombre())
                .municipio(reporte.getColonia().getMunicipio().getNombre())
                .descripcion(reporte.getDescripcion())
                .lat(reporte.getLat())
                .lng(reporte.getLng())
                .direccionAprox(reporte.getDireccionAprox())
                .estatus(reporte.getEstatus())
                .nivelUrgencia(reporte.getNivelUrgencia())
                .anonimo(reporte.getAnonimo())
                .votosConfirmacion(reporte.getVotosConfirmacion())
                .votosFalso(reporte.getVotosFalso())
                .cantidadEvidencias(reporte.getEvidencias().size())
                .createdAt(reporte.getCreatedAt())
                .updatedAt(reporte.getUpdatedAt())
                .resueltoAt(reporte.getResueltoAt())
                .build();
    }
}
