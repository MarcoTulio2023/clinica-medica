package br.edu.imepac.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "pacientes")
@Data
public class PacienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    private String sexo;
    private String temConvenio;
    private Integer codigoConvenio;

}
