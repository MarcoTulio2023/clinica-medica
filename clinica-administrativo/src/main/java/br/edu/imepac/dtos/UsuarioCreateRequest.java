package br.edu.imepac.dtos;

import lombok.Data;

@Data
public class UsuarioCreateRequest {
    private String nome;
    private String senha;
}
