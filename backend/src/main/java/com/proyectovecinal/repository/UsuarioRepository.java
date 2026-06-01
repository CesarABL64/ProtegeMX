package com.proyectovecinal.repository;

import com.proyectovecinal.entity.Usuario;
import com.proyectovecinal.entity.enums.EstatusUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByTelefono(String telefono);

    Optional<Usuario> findByEmailAndEstatus(String email, EstatusUsuario estatus);
}
