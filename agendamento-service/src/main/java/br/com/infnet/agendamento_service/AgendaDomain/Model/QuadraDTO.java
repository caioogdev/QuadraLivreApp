package br.com.infnet.agendamento_service.AgendaDomain.Model;

import java.time.LocalTime;

public record QuadraDTO(String id, LocalTime inicioDefuncionamento, LocalTime finalDeFunionamento) {
}
