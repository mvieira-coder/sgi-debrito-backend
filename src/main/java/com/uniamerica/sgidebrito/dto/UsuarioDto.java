package com.uniamerica.sgidebrito.dto;

import com.uniamerica.sgidebrito.entity.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDto {

    private String login;
    private String senha;
    private String papel;

    public Usuario toEntity() {
        Usuario entidade = new Usuario();
        entidade.setLogin(login);
        entidade.setSenha(senha);
        entidade.setPapel(papel);
        return entidade;
    }
}
