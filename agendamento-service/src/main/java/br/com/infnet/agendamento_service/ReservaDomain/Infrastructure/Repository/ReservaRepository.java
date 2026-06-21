package br.com.infnet.agendamento_service.ReservaDomain.Infrastructure.Repository;

import br.com.infnet.agendamento_service.AgendaDomain.Model.Quadra;
import br.com.infnet.agendamento_service.ReservaDomain.Model.Reserva;
import br.com.infnet.agendamento_service.ReservaDomain.Model.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, UUID> {

    boolean existsByCpfAndDataAndStatus(String cpf, LocalDate data, StatusReserva status);

    @Query("SELECT COUNT(r) > 0 FROM Reserva r WHERE r.quadra.id = :quadraId " +
            "AND r.data = :data " +
            "AND r.status != br.com.infnet.agendamento_service.ReservaDomain.Model.StatusReserva.CANCELADA " +
            "AND (r.horaDeInicio < :fim AND r.horarioDeTermino > :inicio)")
    boolean existeReservaConflitante(
            @Param("quadraId") UUID quadraId,
            @Param("data") LocalDate data,
            @Param("inicio") LocalTime inicio,
            @Param("fim") LocalTime fim
    );


}
