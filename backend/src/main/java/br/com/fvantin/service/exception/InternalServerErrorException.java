package br.com.fvantin.service.exception;

public class InternalServerErrorException extends RuntimeException {

    private static final long serialVersionUID = -7736238268065735919L;

    public InternalServerErrorException() {
        super("Stranger Things occurred... :o");
    }
}
