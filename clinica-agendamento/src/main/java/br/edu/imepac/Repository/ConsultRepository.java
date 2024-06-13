package br.edu.imepac.Repository;

import br.edu.imepac.model.ConsultModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultRepository extends JpaRepository<ConsultModel, Long> {
}
