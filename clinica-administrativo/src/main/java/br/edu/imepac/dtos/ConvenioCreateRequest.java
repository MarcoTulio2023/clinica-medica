package br.edu.imepac.dtos;

import lombok.Data;

@Data
public class ConvenioCreateRequest {
    private Long codigo_convenio;
    private String empresa_convenio;
    private String cnpj;
    private String telefone;
}
