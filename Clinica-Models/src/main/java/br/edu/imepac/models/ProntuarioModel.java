package br.edu.imepac.models;

import br.edu.imepac.model.ConsultModel;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProntuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num_pront;
    private String historico;
    private String receituario;
    private String exames;

    @OneToOne
    private ConsultModel agenda_consulta;

}
