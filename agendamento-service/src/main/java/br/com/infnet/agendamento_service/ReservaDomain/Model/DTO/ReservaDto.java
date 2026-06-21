package br.com.infnet.agendamento_service.ReservaDomain.Model.DTO;

import br.com.infnet.agendamento_service.AgendaDomain.Model.Quadra;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record ReservaDto(
        String cpf,
        UUID quadraId,
        LocalDate data,
        LocalTime inicio,
        LocalTime fim
) {
}
