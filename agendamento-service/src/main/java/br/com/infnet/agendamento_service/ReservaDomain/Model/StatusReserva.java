package br.com.infnet.agendamento_service.ReservaDomain.Model;

public enum StatusReserva {
    PENDENTE("Esta reserva esta pendente"),
    CANCELADA("Esta reserva esta cancelada"),
    ATIVA("Esta reserva esta ativa"),
    CONCLUIDA("Esta reserva esta concluida");

    private final String status;

    StatusReserva(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }

}
