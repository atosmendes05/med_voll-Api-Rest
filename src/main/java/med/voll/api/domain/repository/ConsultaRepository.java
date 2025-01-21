package med.voll.api.domain.repository;

import med.voll.api.domain.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta,Long> {

    Boolean existsByMedicoIdAndData(Long id, LocalDateTime data);
}
