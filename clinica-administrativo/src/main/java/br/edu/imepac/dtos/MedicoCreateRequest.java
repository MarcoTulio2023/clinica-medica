package br.edu.imepac.dtos;

import lombok.Data;

@Data
public class MedicoCreateRequest {
    private Long id;

    private String nome_medico;

    private Long codigo_especialidade;

    private String crm;
    private String senha;

}
