package br.edu.imepac.repositories;

import br.edu.imepac.models.MedicoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*Anotações é tudo o que colocamos um @ na frente como por exemplo esse @Repository*/
/*Ela Serve para indicar algo que melhora na sua classe, como o exemplo a seguir mostra que
tem que conectar no banco de dados*/
@Repository
public interface MedicoRepository extends JpaRepository<MedicoModel, Long> {
}
