package br.com.infnet.agendamento_service.ReservaDomain.Handler;

import br.com.infnet.agendamento_service.AgendaDomain.Model.Quadra;
import br.com.infnet.agendamento_service.AgendaDomain.Repository.QuadraRepository;
import br.com.infnet.agendamento_service.ReservaDomain.Model.DTO.QuadraAtualizadaEvent;
import br.com.infnet.agendamento_service.ReservaDomain.Model.DTO.QuadraRegistradaEvent;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class QuadraEventHandler {
    private final QuadraRepository repository;

    public QuadraEventHandler(QuadraRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void processarNovaQuadra(QuadraRegistradaEvent event) {
        LocalTime horarioDeAbertura = LocalTime.parse(event.horarioDeAbertura());
        LocalTime horarioDeFechamento = LocalTime.parse(event.horarioDeFechamento());

        Quadra novaQuadra = new Quadra(
                event.idQuadra(),
                horarioDeAbertura,
                horarioDeFechamento,
                event.ativa()
        );

        repository.save(novaQuadra);
        System.out.println("Quadra registrada com sucesso: " + event.idQuadra());
    }

    @Transactional
    public void processarAtualizacaoQuadra(QuadraAtualizadaEvent event) {
        repository.findById(event.idQuadra()).ifPresentOrElse(
                quadraExistente -> {

                    LocalTime horarioDeAbertura = LocalTime.parse(event.horarioDeAbertura());
                    LocalTime horarioDeFechamento = LocalTime.parse(event.horarioDeFechamento());

                    quadraExistente.atualizarDados(horarioDeAbertura,horarioDeFechamento, event.ativa());
                    repository.save(quadraExistente);
                    System.out.println("Quadra atualizada com sucesso no banco: " + event.idQuadra());
                },
                () -> {
                    // Cenário excepcional: O evento de atualização chegou antes do evento de criação
                    System.out.println("Aviso: Tentativa de atualizar a quadra " + event.idQuadra() + " mas ela não existe no banco.");
                    // Em cenários reais, poderíamos salvar como uma nova quadra (Upsert) ou lançar um erro para ir para a DLT
                }
        );
    }
}
