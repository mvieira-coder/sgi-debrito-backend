package com.uniamerica.sgidebrito.service;

import com.uniamerica.sgidebrito.entity.Funcionario;
import com.uniamerica.sgidebrito.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    public List<Funcionario> listarTodos() {
        return repository.findAll();
    }

    public Funcionario buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Funcionario nao encontrado"));
    }

    public Funcionario salvar(Funcionario dados) {
        dados.setId(null);
        return repository.save(dados);
    }

    public Funcionario atualizar(Long id, Funcionario dados) {
        Funcionario existente = buscarPorId(id);
        existente.setNome(dados.getNome());
        existente.setCpf(dados.getCpf());
        existente.setTelefone(dados.getTelefone());
        existente.setEmail(dados.getEmail());
        existente.setCargo(dados.getCargo());
        existente.setCategoriaCnh(dados.getCategoriaCnh());
        return repository.save(existente);
    }

    public void excluir(Long id) {
        Funcionario existente = buscarPorId(id);
        repository.delete(existente);
    }
}
