package br.com.infnet.agendamento_service.ReservaDomain.Controller;

import br.com.infnet.agendamento_service.ReservaDomain.Model.DTO.ReservaDto;
import br.com.infnet.agendamento_service.ReservaDomain.Model.Reserva;
import br.com.infnet.agendamento_service.ReservaDomain.Model.StatusReserva;
import br.com.infnet.agendamento_service.ReservaDomain.Service.ReservaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<Reserva> criarReserva(@RequestBody ReservaDto dto) {
        Reserva novaReserva = reservaService.criarReserva(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaReserva);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Reserva> alterarStatus(
            @PathVariable UUID id,
            @RequestBody StatusReserva status) {
        Reserva reservaAtualizada = reservaService.alterarStatus(id, status);
        return ResponseEntity.ok(reservaAtualizada);
    }
}