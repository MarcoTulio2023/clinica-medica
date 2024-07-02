package br.edu.imepac.model;

import br.edu.imepac.models.MedicoModel;
import br.edu.imepac.models.PacienteModel;
import br.edu.imepac.models.UsuarioModel;
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

    @ManyToOne
    @JoinColumn(name = "id_medico")
    private MedicoModel medico;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private PacienteModel paciente;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioModel usuario;


}
