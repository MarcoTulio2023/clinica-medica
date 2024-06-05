package br.edu.imepac.dtos;

import lombok.Data;

@Data
public class EspecialidadeCreateRequest {
    private Long id_especialidade;
    private String nome_especialidade;
    private String descricao;

}
