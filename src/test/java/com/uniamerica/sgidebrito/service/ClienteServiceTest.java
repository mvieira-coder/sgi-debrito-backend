package com.uniamerica.sgidebrito.service;

import com.uniamerica.sgidebrito.entity.Cliente;
import com.uniamerica.sgidebrito.repository.ClienteRepository;
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
class ClienteServiceTest {
    @Mock
    private ClienteRepository repository;
    @InjectMocks
    private ClienteService service;

    @Test
    void listarRegistrosDoRepositorio() {
        List<Cliente> dados = List.of(new Cliente());
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
        Cliente dados = new Cliente();
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
        Cliente dados = new Cliente();
        dados.setId(999L);
        Cliente salvo = new Cliente();
        salvo.setId(8L);
        when(repository.save(dados)).thenReturn(salvo);

        Cliente resultado = service.salvar(dados);

        assertNull(dados.getId());
        assertEquals(8L, resultado.getId());
        verify(repository).save(dados);
    }

    @Test
    void atualizarMantemIdEAlteraTodosOsCampos() {
        Cliente existente = new Cliente();
        existente.setId(7L);
        Cliente dados = new Cliente();
        dados.setId(999L);
        dados.setNome("novo-nome");
        dados.setCpf("novo-cpf");
        dados.setTelefone("novo-telefone");
        dados.setEmail("novo-email");
        when(repository.findById(7L)).thenReturn(Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);
        assertSame(existente, service.atualizar(7L, dados));
        assertEquals(7L, existente.getId());
        assertEquals("novo-nome", existente.getNome());
        assertEquals("novo-cpf", existente.getCpf());
        assertEquals("novo-telefone", existente.getTelefone());
        assertEquals("novo-email", existente.getEmail());
        verify(repository).save(existente);
    }

    @Test
    void atualizarInexistenteNaoSalva() {
        when(repository.findById(7L)).thenReturn(Optional.empty());
        ResponseStatusException erro = assertThrows(ResponseStatusException.class, () -> service.atualizar(7L, new Cliente()));
        assertEquals(HttpStatus.NOT_FOUND, erro.getStatusCode());
        verify(repository, never()).save(any());
    }

    @Test
    void excluirRegistroExistente() {
        Cliente existente = new Cliente();
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
        Cliente dados = new Cliente();
        when(repository.save(dados)).thenThrow(new DataIntegrityViolationException("Falha simulada"));
        assertThrows(DataIntegrityViolationException.class, () -> service.salvar(dados));
        verify(repository).save(dados);
    }
}
