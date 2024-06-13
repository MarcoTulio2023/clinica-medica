package br.edu.imepac.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class PacienteDto {
    private Long id;
    private String nome;
    private String numeroRg;
    private String orgaoEmissor;
    private String numeroCpf;
    private String endereco;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String telefone;
    private String celular;
    private Date dataNascimento;
    private String sexo;
    private String temConvenio;
    private Integer codigoConvenio;
}
