package br.com.infnet.agendamento_service.AgendaDomain.Repository;

import br.com.infnet.agendamento_service.AgendaDomain.Model.Quadra;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuadraRepository extends JpaRepository<Quadra, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT q FROM Quadra q WHERE q.id = :id")
    Optional<Quadra> findByIdWithLock(@Param("id") UUID id);
}
