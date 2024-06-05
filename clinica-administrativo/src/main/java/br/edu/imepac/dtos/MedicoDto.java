package br.edu.imepac.dtos;

import lombok.Data;

@Data
public class MedicoDto {
    private Long id;
    private String nome_medico;
    private Long codigo_especialidade;
    private String crm;
}
