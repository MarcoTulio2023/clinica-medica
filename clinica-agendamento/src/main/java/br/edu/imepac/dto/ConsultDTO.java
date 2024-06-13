package br.edu.imepac.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class ConsultDTO {
    private Long registro_agenda;
    private String hora_agenda;
    private String data_agenda;
    private boolean retorno;
    private boolean cancelado;
    private String motivo_cancelamento;


}
