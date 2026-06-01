package com.proyectovecinal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subcategorias_incidente")
@Data
@NoArgsConstructor
public class SubcategoriaIncidente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subcategoria")
    private Integer idSubcategoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaIncidente categoria;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @OneToMany(mappedBy = "subcategoria")
    private List<Reporte> reportes = new ArrayList<>();
}
