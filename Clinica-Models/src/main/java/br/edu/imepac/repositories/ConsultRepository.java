package br.edu.imepac.repositories;

import br.edu.imepac.model.ConsultModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultRepository extends JpaRepository<ConsultModel, Long> {
}
