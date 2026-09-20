package com.uniamerica.sgidebrito.repository;

import com.uniamerica.sgidebrito.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
