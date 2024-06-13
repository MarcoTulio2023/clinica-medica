package br.edu.imepac.dtos;

import lombok.Data;

@Data
public class ConvenioCreateRequest {
    private String nome;
    private String tipoPlano;
    private String cobertura;
    private String informacoesContato;
}

