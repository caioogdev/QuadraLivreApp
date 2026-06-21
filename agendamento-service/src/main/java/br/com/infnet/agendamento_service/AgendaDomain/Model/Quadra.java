package br.com.infnet.agendamento_service.AgendaDomain.Model;

import br.com.infnet.agendamento_service.ReservaDomain.Model.Reserva;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "quadras")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quadra {
    @Id
    private UUID id;

    @Column(nullable = false)
    private LocalTime inicioDeFuncionamento;

    @Column(nullable = false)
    private LocalTime finalDeFuncionamento;

    @Column(nullable = false)
    private Boolean ativa;

    @OneToMany(cascade = CascadeType.ALL)

    private Set<Reserva> reservas = new HashSet<>();

    public Quadra(UUID id, LocalTime inicioDeFuncionamento, LocalTime finalDeFuncionamento, Boolean ativa){
        this.id = id;
        this.inicioDeFuncionamento = inicioDeFuncionamento;
        this.finalDeFuncionamento = finalDeFuncionamento;
        this.ativa = ativa;
    }

    public void atualizarDados(LocalTime inicioDeFuncionamento,LocalTime finalDeFuncionamento ,boolean novaAtiva) {
        this.inicioDeFuncionamento = inicioDeFuncionamento;
        this.finalDeFuncionamento = finalDeFuncionamento;
        this.ativa = novaAtiva;
    }
}
