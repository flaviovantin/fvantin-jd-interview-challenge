package br.com.fvantin.service.exception;

public class ForbiddenException extends RuntimeException {

    private static final long serialVersionUID = 6793006033898232358L;

    public ForbiddenException() {
        super("Forbidden on Star Wars (swapi.co) server.");
    }
}
