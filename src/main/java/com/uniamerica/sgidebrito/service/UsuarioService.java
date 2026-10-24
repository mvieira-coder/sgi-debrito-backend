package com.uniamerica.sgidebrito.service;

import com.uniamerica.sgidebrito.entity.Usuario;
import com.uniamerica.sgidebrito.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    @Autowired
    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario nao encontrado"));
    }

    public Usuario salvar(Usuario dados) {
        dados.setId(null);
        return repository.save(dados);
    }

    public Usuario atualizar(Long id, Usuario dados) {
        Usuario existente = buscarPorId(id);
        existente.setLogin(dados.getLogin());
        existente.setSenha(dados.getSenha());
        existente.setPapel(dados.getPapel());
        return repository.save(existente);
    }

    public void excluir(Long id) {
        Usuario existente = buscarPorId(id);
        repository.delete(existente);
    }
}
