package com.proyectovecinal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categorias_incidente")
@Data
@NoArgsConstructor
public class CategoriaIncidente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer idCategoria;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "icono_url", length = 500)
    private String iconoUrl;

    @Column(name = "color_hex", length = 7)
    private String colorHex;

    @Column(name = "es_critico", nullable = false)
    private Boolean esCritico = false;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "categoria")
    private List<SubcategoriaIncidente> subcategorias = new ArrayList<>();
}
