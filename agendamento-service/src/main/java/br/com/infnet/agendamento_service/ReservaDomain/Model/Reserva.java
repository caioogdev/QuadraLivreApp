package br.com.infnet.agendamento_service.ReservaDomain.Model;

import br.com.infnet.agendamento_service.AgendaDomain.Model.Quadra;
import br.com.infnet.agendamento_service.ReservaDomain.Exception.ReservaException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "reservas", uniqueConstraints = {
        @UniqueConstraint(name = "uk_cpf_quadra_data", columnNames = {"cpf", "quadra_id", "data"} )
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "cpf",nullable = false)
    private String cpf;

    @Column(name = "data",nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime horaDeInicio;

    @Column(nullable = false)
    private LocalTime horarioDeTermino;

    @Column(nullable = false)
    private StatusReserva status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quadra_id")
    private Quadra quadra;


    public void Cancelar(){
        if(this.status == StatusReserva.CANCELADA){
            throw new ReservaException("Esta reserva ja esta cancelada.");
        }
        this.setStatus(StatusReserva.CANCELADA);
    }


}



