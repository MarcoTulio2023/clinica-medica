package br.edu.imepac.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ConvenioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String tipoPlano;
    private String cobertura;
    private String informacoesContato;
    private String outrasInformacoes;

    public ConvenioModel() {
        // Construtor vazio necessário para o JPA
    }

    public ConvenioModel(String nome, String tipoPlano, String cobertura, String informacoesContato, String outrasInformacoes) {
        this.nome = nome;
        this.tipoPlano = tipoPlano;
        this.cobertura = cobertura;
        this.informacoesContato = informacoesContato;
        this.outrasInformacoes = outrasInformacoes;
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoPlano() {
        return tipoPlano;
    }

    public void setTipoPlano(String tipoPlano) {
        this.tipoPlano = tipoPlano;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public String getInformacoesContato() {
        return informacoesContato;
    }

    public void setInformacoesContato(String informacoesContato) {
        this.informacoesContato = informacoesContato;
    }

    public String getOutrasInformacoes() {
        return outrasInformacoes;
    }

    public void setOutrasInformacoes(String outrasInformacoes) {
        this.outrasInformacoes = outrasInformacoes;
    }
}
