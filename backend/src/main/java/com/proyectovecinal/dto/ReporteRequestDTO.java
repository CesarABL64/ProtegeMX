package com.proyectovecinal.dto;

import com.proyectovecinal.entity.enums.NivelUrgencia;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReporteRequestDTO {

    @NotNull(message = "El ID de subcategoría es obligatorio")
    private Integer idSubcategoria;

    @NotNull(message = "El ID de colonia es obligatorio")
    private Integer idColonia;

    @Size(max = 2000, message = "La descripción no puede exceder 2000 caracteres")
    private String descripcion;

    @NotNull(message = "La latitud es obligatoria")
    @DecimalMin(value = "-90.0", message = "Latitud fuera de rango")
    @DecimalMax(value = "90.0", message = "Latitud fuera de rango")
    private BigDecimal lat;

    @NotNull(message = "La longitud es obligatoria")
    @DecimalMin(value = "-180.0", message = "Longitud fuera de rango")
    @DecimalMax(value = "180.0", message = "Longitud fuera de rango")
    private BigDecimal lng;

    @Size(max = 255, message = "La dirección no puede exceder 255 caracteres")
    private String direccionAprox;

    private NivelUrgencia nivelUrgencia = NivelUrgencia.MEDIO;

    private Boolean anonimo = false;
}
