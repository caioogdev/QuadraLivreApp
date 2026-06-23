package br.com.infnet.agendamento_service.ReservaDomain.Controller;

import br.com.infnet.agendamento_service.ReservaDomain.Model.DTO.ReservaDto;
import br.com.infnet.agendamento_service.ReservaDomain.Model.Reserva;
import br.com.infnet.agendamento_service.ReservaDomain.Model.StatusReserva;
import br.com.infnet.agendamento_service.ReservaDomain.Service.ReservaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private static final Logger logger = LoggerFactory.getLogger(ReservaController.class);
    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<Reserva> criarReserva(@RequestBody ReservaDto dto) {
        logger.info("Criando reserva: courtId={}, userCpf={}", dto.quadraId(), dto.cpf());
        Reserva novaReserva = reservaService.criarReserva(dto);
        logger.info("Reserva criada: id={}", novaReserva.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(novaReserva);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Reserva> alterarStatus(
            @PathVariable UUID id,
            @RequestBody StatusReserva status) {
        logger.info("Alterando reserva: courtId={}, userId={}", id, status);
        Reserva reservaAtualizada = reservaService.alterarStatus(id, status);
        logger.info("Reserva alterada: id={}, statusa={}", id, status);
        return ResponseEntity.ok(reservaAtualizada);
    }
}