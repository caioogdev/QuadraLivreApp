package br.com.infnet.agendamento_service.ReservaDomain.Exception;

public class ReservaException extends RuntimeException{
    public ReservaException(String mensagem){
        super(mensagem);
    }
}
