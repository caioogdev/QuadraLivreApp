package br.com.infnet.agendamento_service.ReservaDomain.Service;

import br.com.infnet.agendamento_service.AgendaDomain.Model.Quadra;
import br.com.infnet.agendamento_service.AgendaDomain.Repository.QuadraRepository;
import br.com.infnet.agendamento_service.ReservaDomain.Exception.ReservaException;
import br.com.infnet.agendamento_service.ReservaDomain.Model.DTO.ReservaDto;
import br.com.infnet.agendamento_service.ReservaDomain.Model.Reserva;
import br.com.infnet.agendamento_service.ReservaDomain.Model.StatusReserva;
import br.com.infnet.agendamento_service.ReservaDomain.Infrastructure.Repository.ReservaRepository;
import br.com.infnet.agendamento_service.ReservaDomain.metrics.ReservasMetrics;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final QuadraRepository quadraRepository;
    private final ReservasMetrics metrics;

    public ReservaService(ReservaRepository reservaRepository, QuadraRepository quadraRepository,ReservasMetrics metrics) {
        this.reservaRepository = reservaRepository;
        this.quadraRepository = quadraRepository;
        this.metrics = metrics;
    }

    @Transactional
    public Reserva criarReserva(ReservaDto dto) {
        metrics.incrementarReservasTotais();

        Quadra quadra = quadraRepository.findByIdWithLock(dto.quadraId())
                .orElseThrow(() -> new ReservaException("Quadra não encontrada."));

        if (Boolean.FALSE.equals(quadra.getAtiva())) {
            metrics.incrementarReservasRejeitadas();
            throw new ReservaException("A quadra selecionada encontra-se inativa ou fechada para reservas.");
        }

        if (dto.inicio().isBefore(quadra.getInicioDeFuncionamento()) || dto.fim().isAfter(quadra.getFinalDeFuncionamento())) {
            metrics.incrementarReservasRejeitadas();
            throw new ReservaException(String.format(
                    "O horário solicitado está fora do período de funcionamento da quadra. Funcionamento: %s às %s.",
                    quadra.getInicioDeFuncionamento(), quadra.getFinalDeFuncionamento()
            ));
        }

        if(!dto.inicio().isBefore(dto.fim())) {
            metrics.incrementarReservasRejeitadas();
            throw new IllegalArgumentException("O horário de início deve ser anterior ao horário de fim.");
        }

        boolean possuiReservaNoDia = reservaRepository.existsByCpfAndDataAndStatus(
                dto.cpf(), dto.data(), StatusReserva.ATIVA);

        if (possuiReservaNoDia) {
            metrics.incrementarReservasRejeitadas();
            throw new ReservaException("O usuário já possui uma reserva ativa para esta data.");

        }

        boolean horarioOcupado = reservaRepository.existeReservaConflitante(
                dto.quadraId(), dto.data(), dto.inicio(), dto.fim());

        if (horarioOcupado) {
            metrics.incrementarReservasRejeitadas();
            throw new ReservaException("O horário solicitado já encontra-se reservado por outro usuário.");
        }

        Reserva novaReserva = new Reserva();
        novaReserva.setCpf(dto.cpf());
        novaReserva.setQuadra(quadra);
        novaReserva.setData(dto.data());
        novaReserva.setHoraDeInicio(dto.inicio());
        novaReserva.setHorarioDeTermino(dto.fim());
        novaReserva.setStatus(StatusReserva.ATIVA);

        Reserva reservaSalva = reservaRepository.save(novaReserva);
        metrics.incrementarReservasCriadas();

        return reservaSalva;

    }

    @Transactional
    public Reserva alterarStatus(UUID reservaId, StatusReserva novoStatus) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ReservaException("Reserva não encontrada."));

        if (novoStatus == StatusReserva.CANCELADA) {
            metrics.incrementarReservasCanceladas();
            reserva.Cancelar();
        } else {
            reserva.setStatus(novoStatus);
        }

        return reservaRepository.save(reserva);
    }
}