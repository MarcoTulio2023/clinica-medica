package br.edu.imepac.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ConsultModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registro_agenda;
    private String data_hora;
    private boolean retorno;
    private boolean cancelado;
    private String motivo_cancelamento;


}
