package com.uniamerica.sgidebrito.repository;

import com.uniamerica.sgidebrito.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
