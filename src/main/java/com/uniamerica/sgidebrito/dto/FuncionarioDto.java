package com.uniamerica.sgidebrito.dto;

import com.uniamerica.sgidebrito.entity.Funcionario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioDto {

    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String cargo;
    private String categoriaCnh;

    public Funcionario toEntity() {
        Funcionario entidade = new Funcionario();
        entidade.setNome(nome);
        entidade.setCpf(cpf);
        entidade.setTelefone(telefone);
        entidade.setEmail(email);
        entidade.setCargo(cargo);
        entidade.setCategoriaCnh(categoriaCnh);
        return entidade;
    }
}
