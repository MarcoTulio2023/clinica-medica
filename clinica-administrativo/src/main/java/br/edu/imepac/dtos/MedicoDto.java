package br.edu.imepac.dtos;

import lombok.Data;

@Data
public class MedicoDto {
    private Long id;
    private String nome;
    private String crm;
    private String senha; // Adicionando a propriedade senha
    private EspecialidadeDto especialidade;
}
