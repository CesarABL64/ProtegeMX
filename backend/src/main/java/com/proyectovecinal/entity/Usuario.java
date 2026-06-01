package com.proyectovecinal.entity;

import com.proyectovecinal.entity.enums.EstatusUsuario;
import com.proyectovecinal.entity.enums.RolUsuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "telefono", unique = true, length = 20)
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false, length = 20)
    private RolUsuario rol = RolUsuario.CIUDADANO;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus", nullable = false, length = 20)
    private EstatusUsuario estatus = EstatusUsuario.ACTIVO;

    @Column(name = "verificado", nullable = false)
    private Boolean verificado = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_colonia")
    private Colonia colonia;

    @Column(name = "puntos_karma", nullable = false)
    private Integer puntosKarma = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "usuario")
    private List<Reporte> reportes = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    private List<Evidencia> evidencias = new ArrayList<>();
}
