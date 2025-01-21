package med.voll.api.domain.repository;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.model.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface PacienteRepository extends JpaRepository<Paciente,Long> {

    Page<Paciente> findAllByAtivoTrue(Pageable paginacao);

    @Query("SELECT COUNT(p) > 0 FROM Paciente p WHERE p.id = :id AND p.ativo = true")
    Boolean findAtivoById(Long id);

    @Query("""
    SELECT COUNT(c) > 0
    FROM Consulta c
    WHERE c.paciente.id = :id
      AND c.data BETWEEN :dataInicio AND :dataFim
""")
    Boolean existsByPacienteIdAndDataBetween(Long id,LocalDateTime dataInicio,LocalDateTime dataFim);

}
