package br.edu.imepac.dto;

import br.edu.imepac.models.MedicoModel;
import lombok.Data;

@Data
public class ConsultCreateRequest {
    private Long registro_agenda;
    private String hora_agenda;
    private String data_agenda;
    private boolean retorno;
    private boolean cancelado;
    private String motivo_cancelamento;
    private MedicoModel medico;

    private String data_hora;

}
