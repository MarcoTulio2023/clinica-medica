package br.edu.imepac.repositories;

import br.edu.imepac.models.PacienteModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<PacienteModel, Long> {
}
