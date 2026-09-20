package com.uniamerica.sgidebrito.service;

import com.uniamerica.sgidebrito.entity.Cliente;
import com.uniamerica.sgidebrito.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public List<Cliente> listarTodos() {
        return repository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cliente nao encontrado"));
    }

    public Cliente salvar(Cliente dados) {
        dados.setId(null);
        return repository.save(dados);
    }

    public Cliente atualizar(Long id, Cliente dados) {
        Cliente existente = buscarPorId(id);
        existente.setNome(dados.getNome());
        existente.setCpf(dados.getCpf());
        existente.setTelefone(dados.getTelefone());
        existente.setEmail(dados.getEmail());
        return repository.save(existente);
    }

    public void excluir(Long id) {
        Cliente existente = buscarPorId(id);
        repository.delete(existente);
    }
}
