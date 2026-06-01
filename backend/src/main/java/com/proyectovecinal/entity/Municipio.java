package com.proyectovecinal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "municipios")
@Data
@NoArgsConstructor
public class Municipio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_municipio")
    private Integer idMunicipio;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "estado", nullable = false, length = 100)
    private String estado;

    @OneToMany(mappedBy = "municipio")
    private List<Colonia> colonias = new ArrayList<>();
}
