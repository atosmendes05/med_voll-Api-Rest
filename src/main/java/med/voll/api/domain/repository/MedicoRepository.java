package med.voll.api.domain.repository;

import med.voll.api.domain.model.Especialidade;
import med.voll.api.domain.model.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico,Long> {

//    @Query("SELECT m FROM Medico m WHERE m.ativo = true")
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    @Query("SELECT m FROM Medico m WHERE m.nome = :nome")
    Optional<Medico> buscaMedicoPorNome(String nome);

    @Query("SELECT m FROM Medico m WHERE m.ativo = true and m.especialidade = :especialidade and m.id not in (select c.medico.id from Consulta c where c.data = :data) order by RANDOM() limit 1")
    Medico escolheMedicoPorEspecialidade(Especialidade especialidade, LocalDateTime data);

    @Query("SELECT COUNT(m) > 0 FROM Medico m WHERE m.id = :id AND m.ativo = true")
    Boolean findAtivoById(Long id);
}
