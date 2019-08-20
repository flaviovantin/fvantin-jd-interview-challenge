package br.com.fvantin.service.exception;

public class ServiceUnavailableException extends RuntimeException {

    private static final long serialVersionUID = -5263990464230736648L;

    public ServiceUnavailableException() {
        super("Service Unavailable on Star Wars (swapi.co) server.");
    }
}
