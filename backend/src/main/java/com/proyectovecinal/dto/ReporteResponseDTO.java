package com.proyectovecinal.dto;

import com.proyectovecinal.entity.enums.EstatusReporte;
import com.proyectovecinal.entity.enums.NivelUrgencia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteResponseDTO {

    private Integer idReporte;
    private String nombreUsuario;
    private String categoriaIncidente;
    private String subcategoria;
    private String colonia;
    private String municipio;
    private String descripcion;
    private BigDecimal lat;
    private BigDecimal lng;
    private String direccionAprox;
    private EstatusReporte estatus;
    private NivelUrgencia nivelUrgencia;
    private Boolean anonimo;
    private Integer votosConfirmacion;
    private Integer votosFalso;
    private Integer cantidadEvidencias;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime resueltoAt;
}
