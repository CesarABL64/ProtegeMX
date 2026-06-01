package com.proyectovecinal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "colonias")
@Data
@NoArgsConstructor
public class Colonia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_colonia")
    private Integer idColonia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_municipio", nullable = false)
    private Municipio municipio;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "codigo_postal", length = 10)
    private String codigoPostal;

    @Column(name = "lat_centro", precision = 9, scale = 6)
    private BigDecimal latCentro;

    @Column(name = "lng_centro", precision = 9, scale = 6)
    private BigDecimal lngCentro;

    @OneToMany(mappedBy = "colonia")
    private List<Usuario> usuarios = new ArrayList<>();

    @OneToMany(mappedBy = "colonia")
    private List<Reporte> reportes = new ArrayList<>();
}
