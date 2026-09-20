package com.uniamerica.sgidebrito.repository;

import com.uniamerica.sgidebrito.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

}