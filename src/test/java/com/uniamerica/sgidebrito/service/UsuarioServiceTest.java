package com.uniamerica.sgidebrito.service;

import com.uniamerica.sgidebrito.entity.Usuario;
import com.uniamerica.sgidebrito.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
    @Mock
    private UsuarioRepository repository;
    @InjectMocks
    private UsuarioService service;

    @Test
    void listarRegistrosDoRepositorio() {
        List<Usuario> dados = List.of(new Usuario());
        when(repository.findAll()).thenReturn(dados);
        assertSame(dados, service.listarTodos());
        verify(repository).findAll();
    }

    @Test
    void listarBancoVazio() {
        when(repository.findAll()).thenReturn(List.of());
        assertTrue(service.listarTodos().isEmpty());
        verify(repository).findAll();
    }

    @Test
    void buscarRegistroExistente() {
        Usuario dados = new Usuario();
        when(repository.findById(7L)).thenReturn(Optional.of(dados));
        assertSame(dados, service.buscarPorId(7L));
        verify(repository).findById(7L);
    }

    @Test
    void buscarInexistenteRetorna404() {
        when(repository.findById(7L)).thenReturn(Optional.empty());
        ResponseStatusException erro = assertThrows(ResponseStatusException.class, () -> service.buscarPorId(7L));
        assertEquals(HttpStatus.NOT_FOUND, erro.getStatusCode());
    }

    @Test
    void cadastroDescartaIdFornecidoPeloCliente() {
        Usuario dados = new Usuario();
        dados.setId(999L);
        Usuario salvo = new Usuario();
        salvo.setId(8L);
        when(repository.save(dados)).thenReturn(salvo);

        Usuario resultado = service.salvar(dados);

        assertNull(dados.getId());
        assertEquals(8L, resultado.getId());
        verify(repository).save(dados);
    }

    @Test
    void atualizarMantemIdEAlteraTodosOsCampos() {
        Usuario existente = new Usuario();
        existente.setId(7L);
        Usuario dados = new Usuario();
        dados.setId(999L);
        dados.setLogin("novo-login");
        dados.setSenha("novo-senha");
        dados.setPapel("novo-papel");
        when(repository.findById(7L)).thenReturn(Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);
        assertSame(existente, service.atualizar(7L, dados));
        assertEquals(7L, existente.getId());
        assertEquals("novo-login", existente.getLogin());
        assertEquals("novo-senha", existente.getSenha());
        assertEquals("novo-papel", existente.getPapel());
        verify(repository).save(existente);
    }

    @Test
    void atualizarInexistenteNaoSalva() {
        when(repository.findById(7L)).thenReturn(Optional.empty());
        ResponseStatusException erro = assertThrows(ResponseStatusException.class, () -> service.atualizar(7L, new Usuario()));
        assertEquals(HttpStatus.NOT_FOUND, erro.getStatusCode());
        verify(repository, never()).save(any());
    }

    @Test
    void excluirRegistroExistente() {
        Usuario existente = new Usuario();
        when(repository.findById(7L)).thenReturn(Optional.of(existente));
        service.excluir(7L);
        verify(repository).delete(existente);
    }

    @Test
    void excluirInexistenteNaoRemoveDados() {
        when(repository.findById(7L)).thenReturn(Optional.empty());
        ResponseStatusException erro = assertThrows(ResponseStatusException.class, () -> service.excluir(7L));
        assertEquals(HttpStatus.NOT_FOUND, erro.getStatusCode());
        verify(repository, never()).delete(any());
    }

    @Test
    void propagarFalhaDePersistencia() {
        Usuario dados = new Usuario();
        when(repository.save(dados)).thenThrow(new DataIntegrityViolationException("Falha simulada"));
        assertThrows(DataIntegrityViolationException.class, () -> service.salvar(dados));
        verify(repository).save(dados);
    }
}
