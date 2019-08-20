package br.com.fvantin.service.exception;

public class MethodNotAllowedException extends RuntimeException {

    private static final long serialVersionUID = -1212286298142839993L;

    public MethodNotAllowedException() {
        super("Method Not Allowed by Star Wars (swapi.co) server.");
    }
}
