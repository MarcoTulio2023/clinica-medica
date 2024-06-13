package br.edu.imepac.dto;

import lombok.Data;

@Data
public class ConsultCreateRequest {
    private Long registro_agenda;
    private String hora_agenda;
    private String data_agenda;
    private boolean retorno;
    private boolean cancelado;
    private String motivo_cancelamento;


    private String data_hora;

}
