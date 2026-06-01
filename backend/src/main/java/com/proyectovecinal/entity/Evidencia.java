package com.proyectovecinal.entity;

import com.proyectovecinal.entity.enums.TipoEvidencia;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "evidencias")
@Data
@NoArgsConstructor
public class Evidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evidencia")
    private Integer idEvidencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reporte", nullable = false)
    private Reporte reporte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 10)
    private TipoEvidencia tipo;

    @Column(name = "url", nullable = false, length = 500)
    private String url;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
