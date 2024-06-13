package br.edu.imepac.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ConsultModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registro_agenda;
    private String hora_agenda;
    private String data_agenda;
    private boolean retorno;
    private boolean cancelado;
    private String motivo_cancelamento;


    private LocalDateTime data_hora_criado;

    @PrePersist
    protected void onCreate() {
        data_hora_criado = LocalDateTime.now();
    }
}
