package br.com.infnet.agendamento_service.ReservaDomain.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class ReservasMetrics {
    private final Counter reservasCriadasCounter;
    private final Counter reservasRejeitasCounter;
    private final Counter reservasTotaisCounter;
    private final Counter reservasCanceladas;

    public ReservasMetrics(MeterRegistry registry){
        this.reservasCriadasCounter = Counter.builder("reservas_criadas")
                .description("Total de reservas realizados com sucesso")
                .tag("service", "agendamento_service")
                .register(registry);
        this.reservasRejeitasCounter = Counter.builder("reservas_rejeitadas")
                .description("Total de reservas rejeitadas ")
                .tag("service", "agendamento_service")
                .register(registry);
        this.reservasTotaisCounter = Counter.builder("reservas_total")
                .description("Total de reservas realizados")
                .tag("service", "agendamento_service")
                .register(registry);
        this.reservasCanceladas = Counter.builder("reservas_canceladas")
                .description("Total de reservas canceladas")
                .tag("service", "agendamento_service")
                .register(registry);
    }

    public void incrementarReservasTotais(){
        reservasTotaisCounter.increment();
    }
    public void incrementarReservasCriadas(){
        reservasCriadasCounter.increment();
    }
    public void incrementarReservasRejeitadas(){
        reservasRejeitasCounter.increment();
    }
    public void incrementarReservasCanceladas(){
        reservasCanceladas.increment();
    }

}
