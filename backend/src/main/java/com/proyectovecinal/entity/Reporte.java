package com.proyectovecinal.entity;

import com.proyectovecinal.entity.enums.EstatusReporte;
import com.proyectovecinal.entity.enums.NivelUrgencia;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reportes")
@Data
@NoArgsConstructor
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reporte")
    private Integer idReporte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_subcategoria", nullable = false)
    private SubcategoriaIncidente subcategoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_colonia", nullable = false)
    private Colonia colonia;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "lat", nullable = false, precision = 9, scale = 6)
    private BigDecimal lat;

    @Column(name = "lng", nullable = false, precision = 9, scale = 6)
    private BigDecimal lng;

    @Column(name = "direccion_aprox", length = 255)
    private String direccionAprox;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus", nullable = false, length = 20)
    private EstatusReporte estatus = EstatusReporte.PENDIENTE;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_urgencia", nullable = false, length = 10)
    private NivelUrgencia nivelUrgencia = NivelUrgencia.MEDIO;

    @Column(name = "anonimo", nullable = false)
    private Boolean anonimo = false;

    @Column(name = "votos_confirmacion", nullable = false)
    private Integer votosConfirmacion = 0;

    @Column(name = "votos_falso", nullable = false)
    private Integer votosFalso = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "resuelto_at")
    private LocalDateTime resueltoAt;

    @OneToMany(mappedBy = "reporte", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evidencia> evidencias = new ArrayList<>();
}
