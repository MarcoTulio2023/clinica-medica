package br.edu.imepac.dto;

import lombok.Data;

@Data
public class ProntuarioCreateRequest {
    private Long num_pront;
    private String historico;
    private String receituario;
    private String exames;
    private br.edu.imepac.model.ConsultModel agenda_consulta;
}
