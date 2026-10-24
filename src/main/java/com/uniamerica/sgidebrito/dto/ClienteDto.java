package com.uniamerica.sgidebrito.dto;

import com.uniamerica.sgidebrito.entity.Cliente;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDto {

    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    public Cliente toEntity() {
        Cliente entidade = new Cliente();
        entidade.setNome(nome);
        entidade.setCpf(cpf);
        entidade.setTelefone(telefone);
        entidade.setEmail(email);
        return entidade;
    }
}
