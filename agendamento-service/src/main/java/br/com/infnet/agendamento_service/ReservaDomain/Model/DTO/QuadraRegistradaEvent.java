package br.com.infnet.agendamento_service.ReservaDomain.Model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record QuadraRegistradaEvent(
         @JsonProperty("courtId") UUID idQuadra,
         @JsonProperty("openTime")String horarioDeAbertura,
         @JsonProperty("closeTime") String horarioDeFechamento,
         @JsonProperty("active") boolean ativa
) {
}
